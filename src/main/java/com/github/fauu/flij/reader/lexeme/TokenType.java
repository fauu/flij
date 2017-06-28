package com.github.fauu.flij.reader.lexeme;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

import com.github.fauu.flij.InitializationError;

import static java.util.stream.Collectors.toMap;

public enum TokenType {

  QUOTE(false, "'"),
  LIST_START(false, "("),
  LIST_END(false, ")"),
  NUMBER(true, Pattern.compile("^(\\+|-)?(\\d)+(\\.(\\d)+)?$")),
  BOOLEAN(true, Pattern.compile("^(true|false)$")),
  STRING(true, Pattern.compile("^\".*\"$")),
  SYMBOL(true, Pattern.compile("^[a-z+-=><\\$\\*/]+$"));

  private boolean atomic;
  private String pattern;
  private Pattern regexPattern;
  
  public static List<TokenType> atomicValues;
  public static List<TokenType> oneCharValues;
  public static Map<Character, TokenType> charToTokenType;

  private TokenType(boolean atomic, String pattern) {
    this(atomic);
    this.pattern = pattern;
  }
  
  private TokenType(boolean atomic, Pattern regexPattern) {
    this(atomic);
    this.regexPattern = regexPattern;
  }
  
  private TokenType(boolean atomic) {
    this.atomic = atomic;
  }
  
  static {
    List<TokenType> atomicValues = new LinkedList<>();
    List<TokenType> oneCharValues = new LinkedList<>();

    for (TokenType value : TokenType.values()) {
      if (value.isAtomic()) {
        atomicValues.add(value);
      } else if (value.isOneChar()) {
        oneCharValues.add(value);
      } else {
        throw new InitializationError("Declared token is neither atomic nor one-char");
      }
    }
    TokenType.atomicValues = Collections.unmodifiableList(atomicValues);
    TokenType.oneCharValues = Collections.unmodifiableList(oneCharValues);

    charToTokenType = oneCharValues.stream().collect(toMap(TokenType::getCharPattern, Function.identity()));
  }
  
  public static boolean existsForChar(char c) {
    return charToTokenType.containsKey(c);
  }
  
  public static TokenType forChar(char c) {
    return charToTokenType.get(c);
  }
  
  public boolean isAtomic() {
    return atomic;
  }
  
  private boolean isOneChar() {
    return pattern.length() == 1;
  }
  
  private boolean isRegex() {
    return regexPattern != null;
  }

  public String getPattern() {
    return pattern;
  }

  public char getCharPattern() {
    if (!isOneChar()) {
      throw new IllegalStateException("TokenType::getCharPattern called for non-one-char token type");
    }
    
    return pattern.charAt(0);
  }
  
  public Pattern getRegexPattern() {
    if (!isRegex()) {
      throw new IllegalStateException("TokenType::getRegexPattern called for non-regex token type");
    }
    
    return regexPattern;
  }

}
