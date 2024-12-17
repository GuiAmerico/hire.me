package com.bemobi.encurtadorurl.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bemobi.encurtadorurl.controller.dto.UrlShortenDTO;
import com.bemobi.encurtadorurl.exceptions.ResourceAlreadyExistsException;
import com.bemobi.encurtadorurl.exceptions.ResourceNotFoundException;
import com.bemobi.encurtadorurl.exceptions.enums.ExceptionType;
import com.bemobi.encurtadorurl.model.Url;
import com.bemobi.encurtadorurl.repository.UrlRepository;
import com.bemobi.encurtadorurl.service.impl.UrlServiceImpl;
import com.bemobi.encurtadorurl.shortener.UrlShortener;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

  @InjectMocks
  UrlServiceImpl urlService;
  @Mock UrlRepository urlRepository;
  @Mock UrlShortener urlShortener;
  static final String BASE_URL = "http://short.url/";
  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(urlService, "BASE_URL", BASE_URL);

  }
  @Test
  void shouldReturnsExistingShortUrlIfUrlAlreadyRegistered() {
    String originalUrl = "https://example.com";
    String alias = "abc123";
    String shortUrl =  BASE_URL.concat(alias);

    Url existingUrl = new Url(originalUrl, shortUrl, alias);

    when(urlRepository.findByOriginalUrl(originalUrl))
      .thenReturn(Optional.of(existingUrl));

    UrlShortenDTO result = urlService.shorten(originalUrl, null);

    assertThat(originalUrl).isEqualTo(result.getOriginalUrl());
    assertThat(shortUrl).isEqualTo(result.getShortUrl());
    assertThat(alias).isEqualTo(result.getAlias());
    verify(urlRepository, never()).save(any());
  }

  @Test
  void shouldCreateUrlWithCustomAlias() {
    String originalUrl = "https://example.com";
    String customAlias = "customAlias";
    String shortUrl = BASE_URL.concat(customAlias);

    Url expectedUrl = new Url(originalUrl, shortUrl, customAlias);

    when(urlRepository.findByOriginalUrl(originalUrl))
      .thenReturn(Optional.empty());
    when(urlRepository.existsByAlias(customAlias))
      .thenReturn(false);
    when(urlRepository.save(any(Url.class)))
      .thenReturn(expectedUrl);

    UrlShortenDTO result = urlService.shorten(originalUrl, customAlias);

    assertThat(originalUrl).isEqualTo(result.getOriginalUrl());
    assertThat(shortUrl).isEqualTo(result.getShortUrl());
    assertThat(customAlias).isEqualTo(result.getAlias());

    verify(urlRepository).save(any(Url.class));
  }

  @Test
  void shouldCreateUrlWithGeneratedAlias() {
    String originalUrl = "http://example.com";
    String generatedAlias = "xyz789";
    String expectedShortUrl = BASE_URL.concat(generatedAlias);
    Url expectedUrl = new Url(originalUrl, expectedShortUrl, generatedAlias);

    when(urlRepository.findByOriginalUrl(originalUrl))
      .thenReturn(Optional.empty());
    when(urlShortener.shorten(originalUrl))
      .thenReturn(generatedAlias);
    when(urlRepository.existsByAlias(generatedAlias))
      .thenReturn(false);
    when(urlRepository.save(any(Url.class)))
      .thenReturn(expectedUrl);

    UrlShortenDTO result = urlService.shorten(originalUrl, null);

    assertThat(originalUrl).isEqualTo(result.getOriginalUrl());
    assertThat(expectedShortUrl).isEqualTo(result.getShortUrl());
    assertThat(generatedAlias).isEqualTo(result.getAlias());
    verify(urlRepository).save(any(Url.class));
  }
  @Test
  void shouldRegenerateAliasIfAlreadyInDatabase() {
    String originalUrl = "http://example.com";
    String customShortUrl = null;
    String alias1 = "abc123";
    String alias2 = "def456";
    String expectedShortUrl = BASE_URL.concat(alias2);


    when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(Optional.empty());
    when(urlShortener.shorten(originalUrl)).thenReturn(alias1, alias2);
    when(urlRepository.existsByAlias(alias1)).thenReturn(true);
    when(urlRepository.existsByAlias(alias2)).thenReturn(false);

    Url savedUrl = new Url(originalUrl, expectedShortUrl, alias2);
    when(urlRepository.save(any(Url.class))).thenReturn(savedUrl);

    UrlShortenDTO result = urlService.shorten(originalUrl, customShortUrl);

    assertEquals(originalUrl, result.getOriginalUrl());
    assertEquals(expectedShortUrl, result.getShortUrl());
    assertEquals(alias2, result.getAlias());
    verify(urlRepository).save(any(Url.class));
    verify(urlShortener, times(2)).shorten(any(String.class));
  }
  @Test
  void shouldThrownExceptionIfCustomAliasAlreadyRegistered() {
    String originalUrl = "http://example.com";
    String customAlias = "customAlias";


    when(urlRepository.existsByAlias(customAlias)).thenReturn(true);

    assertThatThrownBy(() -> urlService.shorten(originalUrl, customAlias))
      .isInstanceOf(ResourceAlreadyExistsException.class)
      .hasMessage("CUSTOM ALIAS ALREADY EXISTS");

  }
  @Test
  void shouldReturnUrlForValidAlias() {
    String alias = "abc123";
    String originalUrl = "http://example.com";
    String expectedShortUrl = BASE_URL.concat(alias);

    Url url = new Url(originalUrl, expectedShortUrl, alias);

    when(urlRepository.findByAlias(alias)).thenReturn(Optional.of(url));

    String result = urlService.retrieve(alias);

    assertThat(originalUrl).isEqualTo(result);
    verify(urlRepository).findByAlias(alias);
  }

  // Throws exception when alias is not found
  @Test
  void shouldThrowExceptionForNotRegisteredAlias() {
    String alias = "nonexistent";

    when(urlRepository.findByAlias(alias)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> urlService.retrieve(alias))
      .isInstanceOf(ResourceNotFoundException.class)
      .hasMessage("SHORTENED URL NOT FOUND");

  }
}