package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SequenceExpression;

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
    SequenceExpression<?, ?> seq = ensureArgumentType(evaluatedArg, SequenceExpression.class);
    
    if (seq.getLength() < 1) {
      return Expression.NIL;
    }

    return seq.subSequence(1, seq.getLength());
  }

}
