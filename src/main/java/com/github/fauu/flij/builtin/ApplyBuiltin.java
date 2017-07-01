package com.github.fauu.flij.builtin;

import java.util.LinkedList;
import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.FunctionExpression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.expression.SymbolExpression;

@RegisteredBuiltin("apply")
public class ApplyBuiltin extends Builtin {

  public ApplyBuiltin(String symbol) {
    super(symbol, n -> n == 2);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    Expression firstArg = ensureArgumentType(arguments.get(0), SymbolExpression.class, FunctionExpression.class);

    List<Expression> newElements = new LinkedList<>();
    newElements.add(firstArg);

    Expression argsArg = arguments.get(1);
    if (argsArg instanceof ListExpression) {
      newElements.addAll(ensureArgumentType(argsArg, ListExpression.class).getElements());
    } else {
      newElements.add(argsArg);
    }

    return evaluator.evaluate(new ListExpression(newElements), environment);
  }

}
