package com.bemobi.encurtadorurl.exceptions.dto;

import com.bemobi.encurtadorurl.exceptions.AbstractException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {

    private String description;
    @JsonProperty("error_code")
    private String errorCode;

    public ExceptionResponse(AbstractException ex) {
        this.description = ex.getMessage();
        this.errorCode = ex.getErrorCode();
    }
}
