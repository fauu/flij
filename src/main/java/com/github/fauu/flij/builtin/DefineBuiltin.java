package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluationException;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SymbolExpression;

public class DefineBuiltin extends Builtin {

  public DefineBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    if (arguments.size() != 2) {
      throw new ExpressionEvaluationException("Symbol definition needs 2 arguments");
    }

    Expression symbolExpr = arguments.get(0);
    if (!(symbolExpr instanceof SymbolExpression)) {
      throw new ExpressionEvaluationException("First argument to symbol definition should be symbol name");
    }

    String symbol = ((SymbolExpression) symbolExpr).getValue();
    Expression definition = evaluator.evaluate(arguments.get(1), environment);
    environment.setDefinition(symbol, definition);

    return Expression.NIL;
  }

}
