package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;
import com.github.fauu.flij.expression.SequenceExpression;

public class LengthBuiltin extends Builtin {

  public LengthBuiltin(String symbol) {
    super(symbol, (n) -> n == 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);
    
    Expression arg = arguments.get(0);
    if (!(arg instanceof SequenceExpression)) {
      rejectArgumentType();
    }
    
    float length = ((SequenceExpression<?>) arg).getLength();

    return new NumberExpression(length);
  }

}
