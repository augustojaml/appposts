package com.augustojaml.appposts.config.auth.exceptions;

public class AuthAuthorizationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AuthAuthorizationException(String message) {
    super(message);
  }
}