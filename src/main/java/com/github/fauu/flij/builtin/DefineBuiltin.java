package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SymbolExpression;

@RegisteredBuiltin("def")
public class DefineBuiltin extends Builtin {
  
  public DefineBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n == 2;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    String symbol = ensureArgumentType(arguments.get(0), SymbolExpression.class).getValue();
    environment.setDefinition(symbol, arguments.get(1));

    return Expression.NIL;
  }

}
