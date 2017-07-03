package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

@RegisteredBuiltin("sqrt")
public class SqrtBuiltin extends Builtin {

  public SqrtBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n == 1;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    double value = ensureArgumentType(arguments.get(0), NumberExpression.class).getValue();

    return new NumberExpression(Math.sqrt(value));
  }

}
