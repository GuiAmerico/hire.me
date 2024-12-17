package com.bemobi.encurtadorurl.controller;

import com.bemobi.encurtadorurl.controller.dto.UrlShortenDTO;
import com.bemobi.encurtadorurl.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/url-shorten")
public class UrlController {

  private final UrlService urlService;


  @PostMapping
  public ResponseEntity<UrlShortenDTO> shortenUrl(@RequestBody @Valid UrlShortenDTO request) {
    UrlShortenDTO response = urlService.shorten(request.getOriginalUrl(), request.getShortUrl());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{shortUrl}")
  public ResponseEntity<Void> retrive(
    @PathVariable String shortUrl,
    HttpServletResponse response
  ) {
    String url = urlService.retrieve(shortUrl);
    try {
      response.sendRedirect(url);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.status(HttpStatus.FOUND).build();
  }

}
