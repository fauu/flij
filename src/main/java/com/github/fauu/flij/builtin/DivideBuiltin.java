package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

public class DivideBuiltin extends Builtin {

  public DivideBuiltin(String symbol) {
    super(symbol, n -> n >= 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    Expression maybeNumber = evaluator.evaluate(arguments.get(0), environment);
    float number = ensureArgumentType(maybeNumber, NumberExpression.class).getValue();
    if (arguments.size() > 1) {
      float result = arguments.stream().skip(1).map(divisorExpr -> {
        Expression evaluatedDivisorExpr = evaluator.evaluate(divisorExpr, environment);

        return ensureArgumentType(evaluatedDivisorExpr, NumberExpression.class).getValue();
      }).reduce(number, (a, b) -> a / b);

      return new NumberExpression(result);
    }

    return new NumberExpression(1 / number);
  }

}
