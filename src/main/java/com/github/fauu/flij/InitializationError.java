package com.github.fauu.flij;

class InitializationError extends Error {

  InitializationError(String msg) {
    super("INITIALIZATION ERROR: " + msg);
  }

}
