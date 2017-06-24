package com.github.fauu.flij.expression;

public interface SequenceExpression<T> {
  
  int getLength();
  
  T getElement(int idx);

}
