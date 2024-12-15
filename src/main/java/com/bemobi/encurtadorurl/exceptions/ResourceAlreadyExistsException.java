package com.bemobi.encurtadorurl.exceptions;

import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;

public class ResourceAlreadyExistsException extends AbstractException {

  public ResourceAlreadyExistsException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
