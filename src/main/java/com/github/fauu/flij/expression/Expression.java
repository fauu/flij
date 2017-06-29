package com.github.fauu.flij.expression;

import com.github.fauu.flij.Evaluable;

public abstract class Expression implements Evaluable {
  
  public static Expression NIL = new ListExpression();

  public String toPrinterString() {
    return toString();
  }
  
}