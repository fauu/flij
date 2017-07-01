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
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n == 1;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    float length = ensureArgumentType(arguments.get(0), SequenceExpression.class).getLength();

    return new NumberExpression(length);
  }

}
