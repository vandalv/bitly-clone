package com.andersen.bitlyclone.data;

import com.andersen.bitlyclone.dto.UrlCreationRequestDTO;
import com.andersen.bitlyclone.entity.UrlMapping;

public class TestDataFactory {

  public static String originalUrl() { return "https://www.google.com/"; }
  public static String shortUrl() { return "shortUrl"; }

  public static UrlMapping urlMapping() {
    return new UrlMapping();
  }
  public static UrlCreationRequestDTO urlCreationRequestDTO() {
    return new UrlCreationRequestDTO();
  }
}
