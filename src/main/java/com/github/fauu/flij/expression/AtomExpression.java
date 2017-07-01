package com.github.fauu.flij.expression;

import java.util.Objects;

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