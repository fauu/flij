package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

@RegisteredBuiltin("=")
public class EqualsBuiltin extends Builtin {

  public EqualsBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    long distinctCount = arguments.stream().map(arg -> evaluator.evaluate(arg, environment)).distinct().count();

    return new BooleanExpression(distinctCount == 1);
  }

}
