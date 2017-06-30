package com.github.fauu.flij.reader.lexeme;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;

public enum TokenType {

  QUOTE('\''),
  LIST_START('('),
  LIST_END(')'),
  COMMENT_START(Pattern.compile("^;;.*$")),
  NUMBER(Pattern.compile("^(\\+|-)?(\\d)+(\\.(\\d)+)?$")),
  CHARACTER(Pattern.compile("^\\\\.$")),
  BOOLEAN(Pattern.compile("^(true|false)$")),
  STRING(Pattern.compile("^\"[^\"]*\"$")),
  SYMBOL(Pattern.compile("^[a-z+-=><\\$\\*/\\?]+$"));

  private char character;
  private Pattern pattern;
  
  public static List<TokenType> characterValues;
  public static List<TokenType> patternValues;
  public static Map<Character, TokenType> charToTokenType;

  private TokenType(char character) {
    this.character = character;
  }
  
  private TokenType(Pattern pattern) {
    this.pattern = pattern;
  }
  
  static {
    List<TokenType> characterValues = new LinkedList<>();
    List<TokenType> patternValues = new LinkedList<>();

    for (TokenType value : TokenType.values()) {
      if (value.isPatternValue()) {
        patternValues.add(value);
      } else if (value.isCharacterValue()) {
        characterValues.add(value);
      }
    }
    TokenType.patternValues = Collections.unmodifiableList(patternValues);
    TokenType.characterValues = Collections.unmodifiableList(characterValues);

    charToTokenType = characterValues.stream().collect(toMap(TokenType::getCharacterValue, Function.identity()));
  }
  
  public static boolean existsForChar(char c) {
    return charToTokenType.containsKey(c);
  }
  
  public static TokenType forChar(char c) {
    return charToTokenType.get(c);
  }
  
  public boolean isPatternValue() {
    return pattern != null;
  }
  
  private boolean isCharacterValue() {
    return character != '\0';
  }
  
  public char getCharacterValue() {
    if (!isCharacterValue()) {
      throw new IllegalStateException("TokenType::getCharacterValue called for non-character token type");
    }
    
    return character;
  }
  
  public Pattern getPatternValue() {
    if (!isPatternValue()) {
      throw new IllegalStateException("TokenType::getPatternValue called for non-pattern token type");
    }
    
    return pattern;
  }

}
