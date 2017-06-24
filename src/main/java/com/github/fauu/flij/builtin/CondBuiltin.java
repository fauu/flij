package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

public class CondBuiltin extends Builtin {

  public CondBuiltin(String symbol) {
    super(symbol, (n) -> n % 2 != 0);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    for (int i = 0; i < arguments.size(); i += 2) {
      Expression genericEvaluatedCondition = evaluator.evaluate(arguments.get(i), environment);
      Expression result = arguments.get(i + 1);

      boolean evaluation = true;
      if (genericEvaluatedCondition instanceof BooleanExpression) {
        evaluation = ((BooleanExpression) genericEvaluatedCondition).getValue();
      }

      if (evaluation) {
        return evaluator.evaluate(result, environment);
      }
    }

    return Expression.NIL;
  }

}
