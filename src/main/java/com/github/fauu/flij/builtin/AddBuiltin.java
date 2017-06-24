package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluationException;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

public class AddBuiltin extends Builtin {

  public AddBuiltin(String symbol) {
    super(symbol);
  }
  
  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator, Environment environment) {
    float sum = arguments.stream().map(arg -> {
      Expression evaluatedArg = evaluator.evaluate(arg, environment);
      if (!(evaluatedArg instanceof NumberExpression)) {
        throw new ExpressionEvaluationException("Function '" + symbol + "' requires arguments of type Number");
      }

      return ((NumberExpression) evaluatedArg).getValue();
    }).reduce(0.0f, Float::sum);

    return new NumberExpression(sum);
  }

}
