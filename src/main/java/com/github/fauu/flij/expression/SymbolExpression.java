package com.github.fauu.flij.expression;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public class SymbolExpression extends AtomExpression<String> {
  
  public SymbolExpression(String value) {
    super(value);
  }
  
  public SymbolExpression(Lexeme lexeme) {
    this.value = lexeme.getValue();
  }
  
}
