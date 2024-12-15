package com.bemobi.encurtadorurl.repository;

import com.bemobi.encurtadorurl.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {
    boolean existsByShortUrl(String shortUrl);

}
