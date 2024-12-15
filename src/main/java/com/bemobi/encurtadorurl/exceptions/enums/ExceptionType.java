package com.bemobi.encurtadorurl.exceptions.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionType {

  CUSTOM_ALIAS_ALREADY_EXISTS("001", "CUSTOM ALIAS ALREADY EXISTS", HttpStatus.CONFLICT),
  SHORTENED_URL_NOT_FOUND("002", "SHORTENED URL NOT FOUND", HttpStatus.NOT_FOUND),
  UNEXPECTED_ERROR("003", "UNEXPECTED ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  private final String code;
  private final String description;
  private final HttpStatus status;

}
