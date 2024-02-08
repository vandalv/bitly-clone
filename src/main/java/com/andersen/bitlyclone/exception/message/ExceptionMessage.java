package com.andersen.bitlyclone.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

  CUSTOM_URL_ALREADY_EXISTS("Custom URL with Such Name Already Exists."),
  INTERNET_PAGE_DOESNT_EXIST("Internet Page From Provided URL Does Not Exist Or Down. Short Link Cant Be Generated"),
  SHORT_URL_ALREADY_EXISTS("Short URL Already Exists. Please Use This Short URL: ");

  private final String message;
}
