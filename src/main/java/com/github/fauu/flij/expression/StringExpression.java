package com.github.fauu.flij.expression;

import java.util.List;

import static java.util.stream.Collectors.toList;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public class StringExpression extends AtomExpression<String> implements SequenceExpression<StringExpression, Character> {

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
  public List<Character> getElements() {
    return value.chars().mapToObj(c -> (char) c).collect(toList());
  }

  @Override
  public StringExpression subSequence(int fromIdx, int toIdx) {
    return new StringExpression(value.substring(fromIdx, toIdx));
  }

  @Override
  public StringExpression reversed() {
    return new StringExpression(new StringBuilder(value).reverse().toString());
  }

  @Override
  public String toString() {
    return "\"" + value + "\"";
  }

}
