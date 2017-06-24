package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

public class SqrtBuiltin extends Builtin {

  public SqrtBuiltin(String symbol) {
    super(symbol, n -> n == 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    Expression evaluatedArg = evaluator.evaluate(arguments.get(0), environment);
    float evaluatedArgValue = ensureArgumentType(evaluatedArg, NumberExpression.class).getValue();

    return new NumberExpression((float) Math.sqrt((double) evaluatedArgValue));
  }

}
