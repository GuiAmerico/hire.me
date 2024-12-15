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
  private long processingTimeInMs;
}
