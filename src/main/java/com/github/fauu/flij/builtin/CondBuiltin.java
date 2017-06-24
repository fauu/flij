package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

public class CondBuiltin extends Builtin {

  public CondBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    if (arguments.size() % 2 != 0) {
      throw new IllegalArgumentException("Builtin cond requires an even number of arguments");
    }

    for (int i = 0; i < arguments.size(); i += 2) {
      Expression rawEvaluatedCondition = evaluator.evaluate(arguments.get(i), environment);
      Expression result = arguments.get(i + 1);

      boolean evaluation = true;
      if (rawEvaluatedCondition instanceof BooleanExpression) {
        evaluation = ((BooleanExpression) rawEvaluatedCondition).getValue();
      }

      if (evaluation) {
        return evaluator.evaluate(result, environment);
      }
    }

    return Expression.NIL;
  }

}
