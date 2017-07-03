package com.github.fauu.flij.evaluator;

class WrongArgumentCountException extends ExpressionEvaluationException {

  public WrongArgumentCountException(String fnName) {
    super("Wrong number of arguments supplied to " + fnName);
  }

}
