package com.bemobi.encurtadorurl.shortener.impl;

import com.bemobi.encurtadorurl.shortener.UrlShortener;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class Base62UrlShortener implements UrlShortener {

  public static final String BASE_62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private final Random random = new Random();

  @Value("${app.short.url.length}")
  private int SHORT_URL_LENGTH;

  @Override
  public String shorten(String url) {
    StringBuilder shortenedUrl = new StringBuilder();
    for (int i = 0; i < SHORT_URL_LENGTH; i++) {
      shortenedUrl.append(BASE_62_CHARACTERS.charAt(random.nextInt(BASE_62_CHARACTERS.length())));
    }
    return shortenedUrl.toString();
  }
}