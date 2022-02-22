package com.augustojaml.appposts.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.augustojaml.appposts.dtos.CommentDTO;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "posts")
public class PostModel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @EqualsAndHashCode.Include
  private String id;

  private Date date;

  private String title;

  private String body;

  @DBRef
  private UserModel author;

  private String image;

  private List<CommentDTO> comments = new ArrayList<>();

  public PostModel(String id, Date date, String title, String body, UserModel author, String image) {
    this.id = id;
    this.date = date;
    this.title = title;
    this.body = body;
    this.author = author;
    this.image = image;
  }

}