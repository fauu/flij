package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

public class MultiplyBuiltin extends Builtin {

  public MultiplyBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    float result = arguments.stream().map(arg -> {
      Expression evaluatedArg = evaluator.evaluate(arg, environment);

      return ensureArgumentType(evaluatedArg, NumberExpression.class).getValue();
    }).reduce(1.0f, (a, b) -> a * b);

    return new NumberExpression(result);
  }

}
