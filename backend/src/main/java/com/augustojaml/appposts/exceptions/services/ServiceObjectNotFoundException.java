package com.augustojaml.appposts.exceptions.services;

public class ServiceObjectNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ServiceObjectNotFoundException(String message) {
    super(message);
  }
}