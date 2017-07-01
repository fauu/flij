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
    double result = IntStream.range(0, arguments.size()).mapToObj(i -> {
      double value = ensureArgumentType(arguments.get(i), NumberExpression.class).getValue();

      return i == 0 ? value : -value;
    }).reduce(0.0, Double::sum);
    
    if (arguments.size() == 1) {
      result *= -1;
    }

    return new NumberExpression(result);
  }

}
