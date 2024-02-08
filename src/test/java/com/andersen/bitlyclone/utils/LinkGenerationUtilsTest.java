package com.andersen.bitlyclone.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LinkGenerationUtilsTest {

  @Test
  void testGenerateLink_Length() {

    String generatedLink = LinkGenerationUtils.generateLink(5);
    assertEquals(5, generatedLink.length(), "Generated link length should be 5");

    generatedLink = LinkGenerationUtils.generateLink(10);
    assertEquals(10, generatedLink.length(), "Generated link length should be 10");
  }

  @Test
  void testGenerateLink_AllowedCharacters() {

    String generatedLink = LinkGenerationUtils.generateLink(5);
    assertTrue(isLinkValid(generatedLink), "Generated link should contain only allowed characters");

    generatedLink = LinkGenerationUtils.generateLink(10);
    assertTrue(isLinkValid(generatedLink), "Generated link should contain only allowed characters");
  }

  private boolean isLinkValid(String link) {

    String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    for (char c : link.toCharArray()) {
      if (allowedCharacters.indexOf(c) == -1) {
        return false;
      }
    }
    return true;
  }
}