package com.github.fauu.flij.expression;

import com.github.fauu.flij.evaluator.Evaluable;

public abstract class Expression implements Evaluable {

  @SuppressWarnings("StaticInitializerReferencesSubClass")
  public static final Expression NIL = new ListExpression();

  public String toPrinterString() {
    return toString();
  }

}