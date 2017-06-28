package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.QuotedExpression;

public class QuoteBuiltin extends Builtin {
  
  public QuoteBuiltin(String symbol) {
    super(symbol, n -> n == 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    return new QuotedExpression(arguments.get(0));
  }

}
