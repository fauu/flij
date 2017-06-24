package com.github.fauu.flij.expression;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public class StringExpression extends AtomExpression<String> implements SequenceExpression<Character> {

  public StringExpression(String value) {
    super(value);
  }

  public StringExpression(Lexeme lexeme) {
    String rawValue = lexeme.getValue();

    this.value = rawValue.substring(1, rawValue.length() - 1);
  }

  @Override
  public int getLength() {
    return value.length();
  }

  @Override
  public Character getElement(int idx) {
    return value.charAt(idx);
  }

  @Override
  public String toString() {
    return "\"" + value + "\"";
  }

}
