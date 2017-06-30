package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;
import com.github.fauu.flij.expression.SequenceExpression;

@RegisteredBuiltin("len")
public class LengthBuiltin extends Builtin {

  public LengthBuiltin(String symbol) {
    super(symbol, n -> n == 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);
    
    Expression evaluatedArg = evaluator.evaluate(arguments.get(0), environment);
    float length = ensureArgumentType(evaluatedArg, SequenceExpression.class).getLength();

    return new NumberExpression(length);
  }

}
