package com.bemobi.encurtadorurl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "url")
public class Url {

  @MongoId
  private String id;
  private String originalUrl;
  private String shortUrl;
  private String alias;

  public Url(
    String originalUrl,
    String shortUrl,
    String alias
  ) {
    this.originalUrl = originalUrl;
    this.shortUrl = shortUrl;
    this.alias = alias;
  }
}
