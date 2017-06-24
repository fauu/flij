package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;
import com.github.fauu.flij.expression.SequenceExpression;

public class SubSequenceBuiltin extends Builtin {

  public SubSequenceBuiltin(String symbol) {
    super(symbol, n -> n == 2 || n == 3);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);
    
    SequenceExpression<?, ?> seq = ensureArgumentType(arguments.get(0), SequenceExpression.class);
    float fromIdx = ensureArgumentType(arguments.get(1), NumberExpression.class).getValue();
    float toIdx = seq.getLength();
    if (arguments.size() == 3) {
      toIdx = ensureArgumentType(arguments.get(2), NumberExpression.class).getValue();
    }

    return seq.subSequence((int) fromIdx, (int) toIdx);
  }

}
