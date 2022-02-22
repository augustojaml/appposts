package com.augustojaml.appposts.exceptions.services;

public class ServiceUploadFileException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ServiceUploadFileException(String message) {
    super(message);
  }
}