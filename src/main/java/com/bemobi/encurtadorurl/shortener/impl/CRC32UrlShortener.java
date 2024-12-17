package com.bemobi.encurtadorurl.shortener.impl;

import com.bemobi.encurtadorurl.shortener.UrlShortener;
import java.util.zip.CRC32;
import org.springframework.stereotype.Component;

@Component
public class CRC32UrlShortener implements UrlShortener {

  @Override
  public String shorten(String url) {
    CRC32 crc = new CRC32();
    crc.update(url.getBytes());
    return Long.toHexString(crc.getValue()).toLowerCase();
  }
}
