package com.augustojaml.appposts.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.augustojaml.appposts.models.PostModel;
import com.augustojaml.appposts.models.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private String id;

  private Date date;

  @NotEmpty(message = "Title required")
  private String title;

  @NotEmpty(message = "Body required")
  private String body;

  @JsonIgnore
  private UserModel author;

  private String image;

  private List<CommentDTO> comments = new ArrayList<>();

  public PostDTO(PostModel post) {
    this.id = post.getId();
    this.date = post.getDate();
    this.title = post.getTitle();
    this.body = post.getBody();
    this.author = post.getAuthor();
    this.image = post.getImage();
    this.comments = post.getComments();
  }

  public AuthorDTO getAuthorPost() {
    return new AuthorDTO(author);
  }

}
