package com.example.taxpayer.api.exception;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException(String message) {

    super(message);
  }
}
