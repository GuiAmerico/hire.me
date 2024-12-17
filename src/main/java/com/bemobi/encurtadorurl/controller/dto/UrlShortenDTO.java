package com.bemobi.encurtadorurl.controller.dto;

import com.bemobi.encurtadorurl.model.Url;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.NotBlank;
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
