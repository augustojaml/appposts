package com.augustojaml.appposts.exceptions.services;

public class ServiceObjectAlreadyExitsException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ServiceObjectAlreadyExitsException(String message) {
    super(message);
  }
}