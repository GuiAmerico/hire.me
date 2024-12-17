package com.bemobi.encurtadorurl.shortener;

import static org.assertj.core.api.Assertions.assertThat;

import com.bemobi.encurtadorurl.shortener.impl.Base62UrlShortener;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UrlShortenerTest {

  @Test
  void shouldShortenerLongUrlToSixCharacteres() {

    UrlShortener urlShortener = new Base62UrlShortener();
    String longUrl = "https://dev.to/campusmvp/java-cmo-listar-filtrar-y-obtener-informacin-de-carpetas-y-archivos-g1i";
    ReflectionTestUtils.setField(urlShortener, "SHORT_URL_LENGTH", 6);

    String shortUrl = urlShortener.shorten(longUrl);
    String shortUrl2 = urlShortener.shorten(longUrl);
    String shortUrl3 = urlShortener.shorten(longUrl);

    assertThat(shortUrl.length()).isEqualTo(6);
    assertThat(shortUrl)
      .isNotEqualTo(shortUrl2)
      .isNotEqualTo(shortUrl3);

  }
}