package com.bemobi.encurtadorurl.exceptions;

import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;

public class ResourceNotFoundException extends AbstractException {

  public ResourceNotFoundException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
