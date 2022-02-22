package com.augustojaml.appposts.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BindValidationErrorException extends StandardErrorException {

  public BindValidationErrorException(Long timestamp, Integer status, String error, String message, String path) {
    super(timestamp, status, error, message, path);
  }

  private static final long serialVersionUID = 1L;

  private List<FieldMessageException> fields = new ArrayList<>();

  public List<FieldMessageException> getErrors() {
    return fields;
  }

  public void addError(String fieldName, String message) {
    fields.add(new FieldMessageException(fieldName, message));
  }
}