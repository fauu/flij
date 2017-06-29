package com.github.fauu.flij.builtin;

import java.util.List;
import java.util.stream.IntStream;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

@RegisteredBuiltin("-")
public class SubBuiltin extends Builtin {

  public SubBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    float result = IntStream.range(0, arguments.size()).mapToObj(i -> {
      Expression evaluatedArg = evaluator.evaluate(arguments.get(i), environment);

      float value = ensureArgumentType(evaluatedArg, NumberExpression.class).getValue();

      return i == 0 ? value : -value;
    }).reduce(0.0f, Float::sum);

    return new NumberExpression(result);
  }

}
