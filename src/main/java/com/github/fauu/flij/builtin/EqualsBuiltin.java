package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SequenceExpression;

@RegisteredBuiltin("=")
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
    boolean result;

    Expression evaluatedFirstArg = evaluator.evaluate(arguments.get(0), environment);
    if (isEmptySequence(evaluatedFirstArg)) {
      result = arguments.stream().skip(1).allMatch(this::isEmptySequence);
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
