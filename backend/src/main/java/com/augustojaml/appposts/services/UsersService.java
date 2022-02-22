package com.augustojaml.appposts.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.augustojaml.appposts.config.auth.user.UserSecurityDetail;
import com.augustojaml.appposts.config.auth.user.UserSecurityDetailAuthenticated;
import com.augustojaml.appposts.dtos.UserDTO;
import com.augustojaml.appposts.exceptions.services.ServiceObjectAlreadyExitsException;
import com.augustojaml.appposts.exceptions.services.ServiceObjectNotFoundException;
import com.augustojaml.appposts.models.RoleModel;
import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.repositories.RolesRepository;
import com.augustojaml.appposts.repositories.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsersService {

  @Autowired
  UsersRepository usersRepository;

  @Autowired
  RolesRepository rolesRepository;

  @Autowired
  FilesService filesService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public UserModel findById(String id) {
    return usersRepository.findById(id).orElseThrow(() -> new ServiceObjectNotFoundException("User not found"));
  }

  public UserModel findByEmail(String email) {
    UserModel user = this.usersRepository.findByEmail(email);
    if (user != null) {
      throw new ServiceObjectAlreadyExitsException("Email already exists");
    }
    return user;
  }

  public UserModel findMe() {
    UserSecurityDetail userSecurityDetail = UserSecurityDetailAuthenticated.authenticated();
    UserModel userModel = this.usersRepository.findByEmail(userSecurityDetail.getUsername());
    return userModel;
  }

  public UserModel save(UserModel userModel, MultipartFile file) {
    this.findByEmail(userModel.getEmail());
    userModel.setPassword(this.bCryptPasswordEncoder.encode(userModel.getPassword()));
    userModel.setAvatar(this.filesService.saveFile(file, "avatar"));
    if (userModel.getRoles().isEmpty()) {
      RoleModel roleModel = this.rolesRepository.findByName("ROLE_USER");
      userModel.setRoles(Arrays.asList(roleModel));
    }
    return this.usersRepository.save(userModel);
  }

  public List<UserModel> findAll() {
    List<UserModel> users = this.usersRepository.findAll();
    return users;
  }

  public UserModel update(String id, UserModel userModel, MultipartFile file) {
    UserModel userById = this.findById(id);

    if (!userById.getEmail().equals(userModel.getEmail())) {
      this.findByEmail(userModel.getEmail());
    }

    if (file != null) {
      this.filesService.removeFile(userById.getAvatar(), "avatar");
      userById.setAvatar(this.filesService.saveFile(file, "avatar"));
    }

    if (!userModel.getRoles().isEmpty()) {
      userById.setRoles(userModel.getRoles());
    }

    userById.setName(userModel.getName());
    userById.setEmail(userModel.getEmail());

    return this.usersRepository.save(userById);
  }

  public void delete(String id) {
    UserModel user = this.findById(id);
    this.usersRepository.delete(user);
  }

  public UserModel fromDTO(UserDTO userDTO) {
    return new UserModel(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(),
        userDTO.getAvatar(), userDTO.getRoles());
  }

  public List<UserDTO> toDTO(List<UserModel> roles) {
    return roles.stream().map(role -> new UserDTO(role)).collect(Collectors.toList());
  }

  public UserDTO toDTO(UserModel userModel) {
    return new UserDTO(userModel);
  }
}
