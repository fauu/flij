package com.github.fauu.flij.expression;

import java.util.Objects;

import com.github.fauu.flij.reader.lexeme.Lexeme;

public abstract class AtomExpression<T> extends Expression {

  protected T value;
  
  protected AtomExpression() { }
  
  public AtomExpression(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }

  public static AtomExpression<?> fromLexeme(Lexeme lexeme) {
    switch (lexeme.getTokenType()) {
      case NUMBER:
        return new NumberExpression(lexeme);
      case BOOLEAN:
        return new BooleanExpression(lexeme);
      case CHARACTER:
        return new CharacterExpression(lexeme);
      case STRING:
        return new StringExpression(lexeme);
      case SYMBOL:
        return new SymbolExpression(lexeme);
      default:
        throw new IllegalArgumentException("Cannot construct atom expression from given token");
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (!(obj instanceof AtomExpression<?>)) {
      return false;
    } else {
      AtomExpression<?> other = (AtomExpression<?>) obj;
      
      return Objects.equals(this.getValue(), other.getValue());
    }
  }

}