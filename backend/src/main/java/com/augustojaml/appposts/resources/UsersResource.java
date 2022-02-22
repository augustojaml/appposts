package com.augustojaml.appposts.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.augustojaml.appposts.dtos.UserDTO;
import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.services.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/users")
public class UsersResource {

  @Autowired
  UsersService usersService;

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> findById(@PathVariable String id) {
    UserModel user = this.usersService.findById(id);
    UserDTO userDTO = new UserDTO(user);
    return ResponseEntity.ok().body(userDTO);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<UserDTO>> findAll() {
    List<UserModel> users = this.usersService.findAll();
    List<UserDTO> usersDTO = users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
    return ResponseEntity.ok().body(usersDTO);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> save(@Valid @ModelAttribute UserDTO userDTO,
      @RequestParam(value = "file", required = false) MultipartFile file) {
    UserModel userModel = this.usersService.save(this.usersService.fromDTO(userDTO), file);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userModel.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<UserDTO> update(@PathVariable String id, @Valid @ModelAttribute UserDTO userDTO,
      @RequestParam(value = "file", required = false) MultipartFile file) {

    UserModel userModel = this.usersService.update(id, this.usersService.fromDTO(userDTO), file);
    return ResponseEntity.ok().body(this.usersService.toDTO(userModel));
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable String id) {
    this.usersService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
