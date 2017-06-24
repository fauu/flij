package com.github.fauu.flij.reader.parser;

import com.github.fauu.flij.reader.ExpressionReadException;

public class ExpressionParseException extends ExpressionReadException {
  
  public ExpressionParseException(String msg) {
    super("Parse error: " + msg);
  }

}
