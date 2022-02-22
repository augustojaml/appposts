package com.augustojaml.appposts.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardErrorException implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long timestamp;

  private Integer status;

  private String error;

  private String message;

  private String path;

}

/**
 * "timestamp": "2022-02-19T23:28:27.627+00:00",
 * "status": 403,
 * "error": "Forbidden",
 * "message": "Access Denied",
 * "path": "/posts"
 */