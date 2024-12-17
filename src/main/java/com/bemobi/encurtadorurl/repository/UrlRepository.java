package com.bemobi.encurtadorurl.repository;

import com.bemobi.encurtadorurl.model.Url;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {
    boolean existsByAlias(String shortUrl);
    Optional<Url> findByAlias(String shortUrl);
    Optional<Url> findByOriginalUrl(String originalUrl);
    List<Url> findTop10ByOrderByAccessCountDesc();
}
