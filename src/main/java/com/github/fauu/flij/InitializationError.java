package com.github.fauu.flij;

public class InitializationError extends Error {
  
  public InitializationError(String msg) {
    super("Initialization error: " + msg);
  }

}
