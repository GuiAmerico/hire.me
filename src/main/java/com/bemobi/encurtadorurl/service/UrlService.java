package com.bemobi.encurtadorurl.service;

import com.bemobi.encurtadorurl.controller.dto.UrlShortenDTO;

public interface UrlService {
  UrlShortenDTO shorten(String url, String customShortUrl);

  String retrieve(String shortUrl);
}
