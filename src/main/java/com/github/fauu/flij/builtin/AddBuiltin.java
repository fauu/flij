package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

@RegisteredBuiltin("+")
public class AddBuiltin extends Builtin {

  public AddBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    double sum = arguments.stream()
        .map(arg -> ensureArgumentType(arg, NumberExpression.class).getValue())
        .reduce(0.0, Double::sum);

    return new NumberExpression(sum);
  }

}
