package com.github.fauu.flij.expression;

import com.github.fauu.flij.reader.lexer.Lexeme;

public class NumberExpression extends AtomExpression<Double> {
  
  public NumberExpression(double value) {
    super(value);
  }
  
  public NumberExpression(Lexeme lexeme) {
    String rawValue = lexeme.getValue();

    this.value = Double.parseDouble(rawValue);
  }
  
}
