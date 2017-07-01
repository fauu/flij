package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

@RegisteredBuiltin("*")
public class MultiplyBuiltin extends Builtin {

  public MultiplyBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    double result = arguments.stream()
        .map(arg -> ensureArgumentType(arg, NumberExpression.class).getValue())
        .reduce(1.0, (a, b) -> a * b);

    return new NumberExpression(result);
  }

}
