package com.andersen.bitlyclone.controller;

import com.andersen.bitlyclone.dto.UrlCreationRequestDTO;
import com.andersen.bitlyclone.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {

  private final LinkService linkService;

  @GetMapping("/{shortUrl}")
  public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String shortUrl) {
    String originalUrl = linkService.getOriginalUrl(shortUrl);
    if (originalUrl != null) {
      return ResponseEntity.status(HttpStatus.FOUND).location(java.net.URI.create(originalUrl)).build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/createShortUrl")
  public ResponseEntity<String> createUrl(@RequestBody UrlCreationRequestDTO requestDTO) {
    String shortUrl = linkService.createUrl(requestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
  }
}
