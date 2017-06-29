package com.github.fauu.flij.builtin;

import java.util.LinkedList;
import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.expression.SymbolExpression;

public class ApplyBuiltin extends Builtin {

  public ApplyBuiltin(String symbol) {
    super(symbol, n -> n == 2);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    SymbolExpression symbol = ensureArgumentType(evaluator.evaluate(arguments.get(0), environment),
        SymbolExpression.class);

    List<Expression> newElements = new LinkedList<>();
    newElements.add(symbol);

    Expression evaluatedArgsArgument = evaluator.evaluate(arguments.get(1), environment);
    if (evaluatedArgsArgument instanceof ListExpression) {
      newElements.addAll(ensureArgumentType(evaluatedArgsArgument, ListExpression.class).getElements());
    } else {
      newElements.add(evaluatedArgsArgument);
    }

    return evaluator.evaluate(new ListExpression(newElements), environment);
  }

}
