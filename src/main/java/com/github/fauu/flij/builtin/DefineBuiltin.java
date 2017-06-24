package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SymbolExpression;

public class DefineBuiltin extends Builtin {
  
  public DefineBuiltin(String symbol) {
    super(symbol, (n) -> n == 2);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    String symbol = ensureArgumentType(arguments.get(0), SymbolExpression.class).getValue();
    Expression definition = evaluator.evaluate(arguments.get(1), environment);
    environment.setDefinition(symbol, definition);

    return Expression.NIL;
  }

}
