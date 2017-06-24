package com.github.fauu.flij.expression;

import com.github.fauu.flij.Evaluable;

public abstract class Expression implements Evaluable {
  
  public static NilExpression NIL = new NilExpression();
  
}