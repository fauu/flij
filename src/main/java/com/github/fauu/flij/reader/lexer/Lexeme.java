package com.github.fauu.flij.reader.lexer;

public class Lexeme {

  private final TokenType tokenType;
  private final String value;

  public Lexeme(TokenType type) {
    this(type, String.valueOf(type.getCharacterValue()));
  }

  public Lexeme(TokenType type, String value) {
    this.tokenType = type;
    this.value = value;
  }

  public TokenType getTokenType() {
    return tokenType;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

}
