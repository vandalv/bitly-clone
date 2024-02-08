package com.andersen.bitlyclone.utils;

import static com.andersen.bitlyclone.utils.Constants.ALLOWED_CHARACTERS;

import java.security.SecureRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkGenerationUtils {

  public static String generateLink(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int randomIndex = new SecureRandom().nextInt(ALLOWED_CHARACTERS.length());
      sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
    }
    return sb.toString();
  }
}
