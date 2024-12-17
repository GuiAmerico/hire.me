package com.bemobi.encurtadorurl.service.impl;

import com.bemobi.encurtadorurl.controller.dto.UrlShortenDTO;
import com.bemobi.encurtadorurl.exceptions.ResourceAlreadyExistsException;
import com.bemobi.encurtadorurl.exceptions.ResourceNotFoundException;
import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;
import com.bemobi.encurtadorurl.model.Url;
import com.bemobi.encurtadorurl.repository.UrlRepository;
import com.bemobi.encurtadorurl.service.UrlService;
import com.bemobi.encurtadorurl.shortener.UrlShortener;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service
public class UrlServiceImpl implements UrlService {

  private final UrlRepository urlRepository;
  private final UrlShortener urlShortener;
  @Value("${app.base-url}")
  private String BASE_URL;
  private static final UriComponentsBuilder RETRIEVE_PATH = UriComponentsBuilder.fromPath("/api/v1/url-shorten/{shortUrl}");

  @Override
  public UrlShortenDTO shorten(String originalUrl, String customShortUrl) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Optional<Url> urlOptional = urlRepository.findByOriginalUrl(originalUrl);

    if (urlOptional.isPresent()) {
      stopWatch.stop();
      long processingTime = stopWatch.getTotalTimeMillis();
      return new UrlShortenDTO(urlOptional.get(), processingTime);
    }

    String alias;

    do {
      alias = getShortUrl(originalUrl, customShortUrl);
    } while (urlRepository.existsByAlias(alias));

    final String shortUrl = BASE_URL.concat(RETRIEVE_PATH.build(alias).getPath());
    Url url = new Url(originalUrl, shortUrl, alias);
    Url savedUrl = urlRepository.save(url);

    stopWatch.stop();
    long processingTime = stopWatch.getTotalTimeMillis();
    return new UrlShortenDTO(savedUrl, processingTime);
  }


  @Override
  public String retrieve(String alias) {
    return urlRepository
      .findByAlias(alias)
      .map(Url::getOriginalUrl)
      .orElseThrow(() -> new ResourceNotFoundException(ExceptionType.SHORTENED_URL_NOT_FOUND));
  }

  private String getShortUrl(String url, String customShortUrl) {
    if (Objects.nonNull(customShortUrl)) {
      validateCustomShortUrl(customShortUrl);
      return customShortUrl;
    }

    String generatedUrl;
    do {
      generatedUrl = urlShortener.shorten(url);
    } while (urlRepository.existsByAlias(generatedUrl));

    return generatedUrl;
  }

  private void validateCustomShortUrl(String customShortUrl) {
    if (urlRepository.existsByAlias(customShortUrl)) {
      throw new ResourceAlreadyExistsException(ExceptionType.CUSTOM_ALIAS_ALREADY_EXISTS);
    }
  }

}
