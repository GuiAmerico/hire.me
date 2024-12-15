package com.bemobi.encurtadorurl.exceptions;

import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;

public class BusinessException extends AbstractException {


  public BusinessException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
