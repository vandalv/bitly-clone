package com.andersen.bitlyclone.exception.handler;

import com.andersen.bitlyclone.exception.CustomUrlExistsException;
import com.andersen.bitlyclone.exception.InternetPageDoesNotExistException;
import com.andersen.bitlyclone.exception.ShortUrlAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(CustomUrlExistsException.class)
  public ResponseEntity<String> customUrlExists(CustomUrlExistsException exception) {

    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
  }

  @ExceptionHandler(InternetPageDoesNotExistException.class)
  public ResponseEntity<String> internetPageDoesNotExist(InternetPageDoesNotExistException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }

  @ExceptionHandler(ShortUrlAlreadyExistsException.class)
  public ResponseEntity<String> shortUrlAlreadyExists(ShortUrlAlreadyExistsException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
  }
}
