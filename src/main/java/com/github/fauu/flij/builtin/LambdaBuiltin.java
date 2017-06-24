package com.github.fauu.flij.builtin;

import java.util.List;

import static java.util.stream.Collectors.toList;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.FunctionExpression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.expression.SymbolExpression;

public class LambdaBuiltin extends Builtin {

  public LambdaBuiltin(String symbol) {
    super(symbol, (n) -> n == 2);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    Expression argumentListExpr = arguments.get(0);
    if (!(argumentListExpr instanceof ListExpression)) {
      rejectArgumentType();
    }

    ListExpression argumentList = (ListExpression) argumentListExpr;
    List<String> argumentSymbols = argumentList.getChildren().stream().map(arg -> {
      if (!(arg instanceof SymbolExpression)) {
        rejectArgumentType();
      }

      return ((SymbolExpression) arg).getValue();
    }).collect(toList());

    return new FunctionExpression(argumentSymbols, arguments.get(1));
  }

}
