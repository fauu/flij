package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;

@RegisteredBuiltin("tail")
public class TailBuiltin extends Builtin {

  public TailBuiltin(String symbol) {
    super(symbol, n -> n == 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    Expression evaluatedArg = evaluator.evaluate(arguments.get(0), environment);
    ListExpression list = ensureArgumentType(evaluatedArg, ListExpression.class);
    
    if (list.getLength() < 1) {
      return Expression.NIL;
    }

    return new ListExpression(list.getElements().subList(1, list.getLength()));
  }

}
