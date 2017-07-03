package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

@RegisteredBuiltin("cond")
@Shortcircuiting
public class CondBuiltin extends Builtin {

  public CondBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n % 2 == 0;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    Expression currentResult = Expression.NIL;
    for (int i = 0; i < arguments.size(); i += 2) {
      BooleanExpression evaluatedCondition = 
          BooleanExpression.fromExpression(evaluator.evaluate(arguments.get(i), environment));

      currentResult = arguments.get(i + 1);
      if (evaluatedCondition.getValue()) {
        break;
      }
    }

    return evaluator.evaluate(currentResult, environment);
  }

}
