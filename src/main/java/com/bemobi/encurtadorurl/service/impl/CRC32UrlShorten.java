package com.bemobi.encurtadorurl.service.impl;

import com.bemobi.encurtadorurl.service.UrlShorten;
import java.util.zip.CRC32;
import org.springframework.stereotype.Component;

@Component
public final class CRC32UrlShorten implements UrlShorten {

  @Override
  public String shorten(String url) {
    CRC32 crc = new CRC32();
    crc.update(url.getBytes());
    return Long.toHexString(crc.getValue()).toUpperCase();
  }
}
