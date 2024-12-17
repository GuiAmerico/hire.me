package com.bemobi.encurtadorurl.controller.dto;

import com.bemobi.encurtadorurl.model.Url;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UrlShortenDTO {

  @NotBlank(message = "The original URL is required")
  @JsonProperty("original_url")
  private String originalUrl;
  @JsonProperty("short_url")
  private String shortUrl;
  private String alias;
  @JsonProperty("processing_time_in_ms")
  private long processingTimeInMs;

  public UrlShortenDTO(Url savedUrl, long processingTime) {
    this.originalUrl = savedUrl.getOriginalUrl();
    this.shortUrl = savedUrl.getShortUrl();
    this.alias = savedUrl.getAlias();
    this.processingTimeInMs = processingTime;
  }
}
