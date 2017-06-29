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

    Expression evaluatedFirstArg = evaluator.evaluate(arguments.get(0), environment);
    Expression checkedFirstArg = ensureArgumentType(evaluatedFirstArg, SymbolExpression.class, FunctionExpression.class);

    List<Expression> newElements = new LinkedList<>();
    newElements.add(checkedFirstArg);

    Expression evaluatedArgsArg = evaluator.evaluate(arguments.get(1), environment);
    if (evaluatedArgsArg instanceof ListExpression) {
      newElements.addAll(ensureArgumentType(evaluatedArgsArg, ListExpression.class).getElements());
    } else {
      newElements.add(evaluatedArgsArg);
    }

    return evaluator.evaluate(new ListExpression(newElements), environment);
  }

}
