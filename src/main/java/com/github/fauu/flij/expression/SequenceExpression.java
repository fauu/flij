package com.github.fauu.flij.expression;

public interface SequenceExpression<S extends Expression, T> {
  
  int getLength();
  
  T getElement(int idx);
  
  S subSequence(int fromIdx, int toIdx);
  
  S reversed();

}
