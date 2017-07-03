package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

@RegisteredBuiltin("or")
@Shortcircuiting
public class OrBuiltin extends Builtin {

  public OrBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    boolean anyTrue = arguments.stream()
        .anyMatch(arg -> BooleanExpression.fromExpression(evaluator.evaluate(arg, environment)).isTrue());

    return new BooleanExpression(anyTrue);
  }

}
