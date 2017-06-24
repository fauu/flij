package com.github.fauu.flij.expression;

import java.util.List;

public interface SequenceExpression<S extends Expression, T> {
  
  int getLength();
  
  T getElement(int idx);
  
  List<T> getElements();
  
  S subSequence(int fromIdx, int toIdx);
  
  S reversed();

}
