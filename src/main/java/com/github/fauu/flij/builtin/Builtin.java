package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.Evaluable;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;

public abstract class Builtin implements Evaluable {

  protected String symbol;

  protected Builtin(String symbol) {
    this.symbol = symbol;
  }

  public abstract Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment);

  public String getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return "<builtin>";
  }

}
