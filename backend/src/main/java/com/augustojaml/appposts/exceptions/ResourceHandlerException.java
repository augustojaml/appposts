package com.augustojaml.appposts.exceptions;

import javax.servlet.http.HttpServletRequest;

import com.augustojaml.appposts.config.auth.exceptions.JWTAuthTokenExpiredException;
import com.augustojaml.appposts.exceptions.services.ServiceObjectAlreadyExitsException;
import com.augustojaml.appposts.exceptions.services.ServiceObjectNotFoundException;
import com.augustojaml.appposts.exceptions.services.ServiceUploadFileException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceHandlerException {

  @ExceptionHandler({ BindException.class })
  public ResponseEntity<StandardErrorException> methodArgumentNotValid(BindException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.UNPROCESSABLE_ENTITY.value();
    BindValidationErrorException error = new BindValidationErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
        "Fields Required or invalid",
        request.getRequestURI());

    for (FieldError field : ex.getBindingResult().getFieldErrors()) {
      error.addError(field.getField(), field.getDefaultMessage());
    }

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @ExceptionHandler(ServiceObjectNotFoundException.class)
  public ResponseEntity<StandardErrorException> objectNotFound(ServiceObjectNotFoundException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<StandardErrorException> exception(HttpMessageNotReadableException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(ServiceObjectAlreadyExitsException.class)
  public ResponseEntity<StandardErrorException> exception(ServiceObjectAlreadyExitsException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(ServiceUploadFileException.class)
  public ResponseEntity<StandardErrorException> exception(ServiceUploadFileException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<StandardErrorException> exception(HttpRequestMethodNotSupportedException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<StandardErrorException> exception(HttpMediaTypeNotSupportedException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(JWTAuthTokenExpiredException.class)
  public ResponseEntity<StandardErrorException> exception(JWTAuthTokenExpiredException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.BAD_REQUEST.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<StandardErrorException> exception(AccessDeniedException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.FORBIDDEN.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<StandardErrorException> exception(UsernameNotFoundException ex,
      HttpServletRequest request) {

    int httpStatus = HttpStatus.FORBIDDEN.value();
    StandardErrorException error = new StandardErrorException(System.currentTimeMillis(), httpStatus,
        HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
    return ResponseEntity.status(httpStatus).body(error);
  }
}
