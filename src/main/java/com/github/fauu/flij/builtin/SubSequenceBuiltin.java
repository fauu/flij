package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;
import com.github.fauu.flij.expression.SequenceExpression;

@RegisteredBuiltin("subseq")
public class SubSequenceBuiltin extends Builtin {

  public SubSequenceBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n == 2 || n == 3;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    SequenceExpression<?, ?> seq = ensureArgumentType(arguments.get(0), SequenceExpression.class);
    double fromIdx = ensureArgumentType(arguments.get(1), NumberExpression.class).getValue();
    double toIdx = seq.getLength();
    if (arguments.size() == 3) {
      toIdx = ensureArgumentType(arguments.get(2), NumberExpression.class).getValue();
    }
    
    if (fromIdx < 0 || fromIdx > toIdx) {
      return Expression.NIL;
    }

    return seq.subSequence((int) fromIdx, (int) toIdx);
  }

}
