package com.augustojaml.appposts.config.auth.exceptions;

public class JWTAuthTokenExpiredException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JWTAuthTokenExpiredException(String message) {
    super(message);
  }
}