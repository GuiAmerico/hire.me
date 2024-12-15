package com.bemobi.encurtadorurl.exceptions;

import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractException extends RuntimeException {

  private final String errorCode;
  private HttpStatus status;

  protected AbstractException(ExceptionType exceptionType) {
    super(exceptionType.getDescription());
    this.errorCode = exceptionType.getCode();
    this.status = exceptionType.getStatus();
  }
}
