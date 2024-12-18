package com.bemobi.encurtadorurl.controller.dto;

import com.bemobi.encurtadorurl.model.Url;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class UrlShortenDTO {

  @NotBlank(message = "The original URL is required")
  @JsonProperty("original_url")
  private String originalUrl;
  @JsonProperty(value = "short_url")
  private String shortUrl;
  @Pattern(regexp = "^[a-zA-Z0-9]{1,10}$", message = "The short URL must have between 1 and 10 alphanumeric characters")
  private String alias;
  @JsonProperty(value = "processing_time_in_ms")
  private Long processingTimeInMs;
  @JsonProperty(value = "access_count")
  private long accessCount;

  public UrlShortenDTO(Url savedUrl, Long processingTime) {
    this.originalUrl = savedUrl.getOriginalUrl();
    this.shortUrl = savedUrl.getShortUrl();
    this.alias = savedUrl.getAlias();
    this.processingTimeInMs = processingTime;
    this.accessCount = savedUrl.getAccessCount();
  }
}
