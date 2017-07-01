package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SequenceExpression;

@RegisteredBuiltin("=")
@Shortcircuiting
public class EqualsBuiltin extends Builtin {

  public EqualsBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n >= 1;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    boolean result = true;

    Expression evaluatedFirstArg = evaluator.evaluate(arguments.get(0), environment);
    if (isEmptySequence(evaluatedFirstArg)) {
      result = !arguments.stream().skip(1).anyMatch(arg -> !isEmptySequence(evaluator.evaluate(arg, environment)));
    } else {
      long distinctCount = arguments.stream().map(arg -> evaluator.evaluate(arg, environment)).distinct().count();
      result = distinctCount == 1;
    }

    return new BooleanExpression(result);
  }

  private boolean isEmptySequence(Expression expression) {
    return (expression instanceof SequenceExpression && ((SequenceExpression<?, ?>) expression).isEmpty());
  }

}
