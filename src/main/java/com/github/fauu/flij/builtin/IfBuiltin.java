package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

public class IfBuiltin extends Builtin {

  public IfBuiltin(String symbol) {
    super(symbol, (n) -> n == 3);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);
    
    boolean evaluatedCondition = 
        BooleanExpression.fromExpression(evaluator.evaluate(arguments.get(0), environment)).getValue();

    int resultIdx = evaluatedCondition ? 1 : 2;

    return evaluator.evaluate(arguments.get(resultIdx), environment);
  }

}
