package com.augustojaml.appposts.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.augustojaml.appposts.dtos.CommentDTO;
import com.augustojaml.appposts.dtos.PostDTO;
import com.augustojaml.appposts.models.PostModel;
import com.augustojaml.appposts.services.PostsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

  @Autowired
  PostsService postsService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<PostDTO> findById(@PathVariable String id) {
    PostModel post = this.postsService.findById(id);
    PostDTO postDTO = new PostDTO(post);
    return ResponseEntity.ok().body(postDTO);
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<PostDTO>> findAll() {
    List<PostModel> posts = this.postsService.findAll();
    List<PostDTO> postDTOs = posts.stream().map(post -> new PostDTO(post)).collect(Collectors.toList());
    return ResponseEntity.ok().body(postDTOs);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseEntity<Void> save(@Valid @ModelAttribute PostDTO postDTO,
      @RequestParam(value = "file", required = false) MultipartFile file) {
    PostModel postModel = this.postsService.save(this.postsService.fromDTO(postDTO), file);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(postModel.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @RequestMapping(value = "/{id}/comment", method = RequestMethod.POST)
  public ResponseEntity<CommentDTO> comment(@PathVariable String id, @RequestBody CommentDTO commentDTO) {
    CommentDTO comment = this.postsService.comment(id, commentDTO);
    return ResponseEntity.ok().body(comment);
  }
}
