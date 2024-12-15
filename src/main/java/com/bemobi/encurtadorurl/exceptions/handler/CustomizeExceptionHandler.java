package com.bemobi.encurtadorurl.exceptions.handler;

import com.bemobi.encurtadorurl.exceptions.AbstractException;
import com.bemobi.encurtadorurl.exceptions.dto.ExceptionResponse;
import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class CustomizeExceptionHandler {

  @ExceptionHandler(AbstractException.class)
  public ResponseEntity<ExceptionResponse> handleExceptionAbstractException(
    AbstractException ex
  ) {
    log.error(this.causedIn(ex));
    return new ResponseEntity<>(new ExceptionResponse(ex), ex.getStatus());
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleAllException(
    Exception ex
  ) {
    log.error(this.causedIn(ex));
    ExceptionType unexpectedError = ExceptionType.UNEXPECTED_ERROR;
    ExceptionResponse exceptionResponse = new ExceptionResponse(
      ex.getMessage(),
      unexpectedError.getCode()
    );
    return new ResponseEntity<>(exceptionResponse, unexpectedError.getStatus());
  }


  private String causedIn(Exception ex) {
    StringBuilder cause = new StringBuilder();
    StackTraceElement ste = ex.getStackTrace()[0];
    cause
      .append("Classe: ")
      .append(ste.getClassName()).append(" -  Linha: ")
      .append(ste.getLineNumber())
      .append(" - Mensagem: ")
      .append(ex.getMessage());
    return cause.toString();
  }

}
