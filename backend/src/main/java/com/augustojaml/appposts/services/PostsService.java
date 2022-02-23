package com.augustojaml.appposts.services;

import java.sql.Date;
import java.util.List;

import com.augustojaml.appposts.dtos.AuthorDTO;
import com.augustojaml.appposts.dtos.CommentDTO;
import com.augustojaml.appposts.dtos.PostDTO;
import com.augustojaml.appposts.exceptions.services.ServiceObjectNotFoundException;
import com.augustojaml.appposts.models.PostModel;
import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.repositories.PostsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostsService {

  @Autowired
  PostsRepository postsRepository;

  @Autowired
  UsersService usersService;

  @Autowired
  FilesService filesService;

  public PostModel findById(String id) {
    return postsRepository.findById(id).orElseThrow(() -> new ServiceObjectNotFoundException("Post not found"));
  }

  public List<PostModel> findAll() {
    return this.postsRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
  }

  public PostModel save(PostModel postModel, MultipartFile file) {
    postModel.setImage(this.filesService.saveFile(file, "post"));
    postModel.setDate(new Date(System.currentTimeMillis()));
    postModel.setAuthor(this.usersService.findMe());
    return this.postsRepository.save(postModel);
  }

  public CommentDTO comment(String id, CommentDTO commentDTO) {
    PostModel postModel = this.findById(id);
    if (postModel == null) {
      throw new ServiceObjectNotFoundException("Post not found");
    }

    UserModel userModel = this.usersService.findMe();

    commentDTO.setDate(new Date(System.currentTimeMillis()));
    commentDTO.setAuthor(new AuthorDTO(userModel));
    postModel.getComments().add(commentDTO);
    this.postsRepository.save(postModel);
    return commentDTO;
  }

  public PostModel fromDTO(PostDTO post) {
    return new PostModel(post.getId(), post.getDate(), post.getTitle(), post.getBody(),
        post.getAuthor(), post.getImage());
  }

  public PostModel toDTO(PostModel post) {
    return new PostModel(post.getId(), post.getDate(), post.getTitle(), post.getBody(),
        post.getAuthor(), post.getImage());
  }
}
