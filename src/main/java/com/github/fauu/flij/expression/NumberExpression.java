package com.github.fauu.flij.expression;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public class NumberExpression extends AtomExpression<Float> {
  
  public NumberExpression(float value) {
    super(value);
  }
  
  public NumberExpression(Lexeme lexeme) {
    String rawValue = lexeme.getValue();

    this.value = Float.parseFloat(rawValue);
  }
  
}
