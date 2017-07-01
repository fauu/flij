package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

@RegisteredBuiltin("and")
@Shortcircuiting
public class AndBuiltin extends Builtin {

  public AndBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    boolean anyFalse = arguments.stream()
        .anyMatch(arg -> BooleanExpression.fromExpression(evaluator.evaluate(arg, environment)).isFalse());

    return new BooleanExpression(!anyFalse);
  }

}
