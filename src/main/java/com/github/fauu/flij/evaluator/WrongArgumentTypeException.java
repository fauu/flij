package com.github.fauu.flij.evaluator;

public class WrongArgumentTypeException extends ExpressionEvaluationException {

  public WrongArgumentTypeException(String fnName) {
    super("Wrong type of arguments supplied to " + fnName);
  }

}
