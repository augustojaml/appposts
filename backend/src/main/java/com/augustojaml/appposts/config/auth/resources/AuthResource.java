package com.augustojaml.appposts.config.auth.resources;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augustojaml.appposts.config.auth.JWT.JWTUtil;
import com.augustojaml.appposts.dtos.UserDTO;
import com.augustojaml.appposts.models.UserModel;
import com.augustojaml.appposts.services.UsersService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

  @Autowired
  JWTUtil jwtUtil;

  @Autowired
  UsersService usersService;

  @RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
  public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    this.jwtUtil.signInWithRefreshToken();
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/me", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> findMe() {
    UserModel userModel = this.usersService.findMe();
    UserDTO userDTO = new UserDTO(userModel);
    return ResponseEntity.ok().body(userDTO);
  }
}
