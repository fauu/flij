package com.github.fauu.flij.expression;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public class BooleanExpression extends AtomExpression<Boolean> {
  
  public BooleanExpression(Boolean value) {
    super(value);
  }
  
  public BooleanExpression(Lexeme lexeme) {
    String strValue = lexeme.getValue();
    
    switch (strValue) {
      case "true":
        value = true;
        return;
      case "false":
        value = false;
        return;
      default:
        throw new IllegalArgumentException("Cannot construct BooleanExpression");
    }
  }

  public boolean isTrue() {
    return value == true;
  }
  
  public boolean isFalse() {
    return value == false;
  }
  
  public static BooleanExpression fromExpression(Expression expression) {
    boolean value = true;

    if (expression instanceof BooleanExpression && !((BooleanExpression) expression).getValue()) {
      value = false;
    }
    
    return new BooleanExpression(value);
  }
  
}
