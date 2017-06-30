package com.github.fauu.flij.expression;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public class CharacterExpression extends AtomExpression<Character> {
  
  public CharacterExpression(char value) {
    super(value);
  }
  
  public CharacterExpression(Lexeme lexeme) {
    String rawValue = lexeme.getValue();

    this.value = rawValue.charAt(1);
  }

  @Override
  public String toPrinterString() {
    return "\\" + value;
  }
  
}
