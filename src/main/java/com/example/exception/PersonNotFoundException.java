package com.example.exception;

/**
 * @author: clong
 * @date: 2017-03-23
 */
public class PersonNotFoundException extends RuntimeException {

  public PersonNotFoundException() {
  }

  public PersonNotFoundException(String message) {
    super(message);
  }
}
