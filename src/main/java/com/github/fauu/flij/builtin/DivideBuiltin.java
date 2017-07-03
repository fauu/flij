package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

@RegisteredBuiltin("/")
public class DivideBuiltin extends Builtin {

  public DivideBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n >= 1;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    double number = ensureArgumentType(arguments.get(0), NumberExpression.class).getValue();
    if (arguments.size() > 1) {
      double result = arguments.stream().skip(1).map(divisorExpr -> {
        return ensureArgumentType(divisorExpr, NumberExpression.class).getValue();
      }).reduce(number, (a, b) -> a / b);

      return new NumberExpression(result);
    }

    return new NumberExpression(1 / number);
  }

}
