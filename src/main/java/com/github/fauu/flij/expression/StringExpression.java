package com.github.fauu.flij.expression;

import java.util.List;

import com.github.fauu.flij.reader.lexer.Lexeme;

import static java.util.stream.Collectors.toList;

public class StringExpression extends AtomExpression<String>
    implements SequenceExpression<StringExpression, CharacterExpression> {

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
  public CharacterExpression getElement(int idx) {
    return new CharacterExpression(value.charAt(idx));
  }

  @Override
  public List<CharacterExpression> getElements() {
    return value.chars().mapToObj(c -> new CharacterExpression((char) c)).collect(toList());
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
  public String toPrinterString() {
    return "\"" + value + "\"";
  }

}
