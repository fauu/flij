package com.github.fauu.flij.evaluator;

public class WrongArgumentCountException extends ExpressionEvaluationException {

  public WrongArgumentCountException(String fnName) {
    super("Wrong number of arguments supplied to " + fnName);
  }

}
