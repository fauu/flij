package com.github.fauu.flij.evaluator;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

import com.github.fauu.flij.Evaluable;
import com.github.fauu.flij.builtin.Builtin;
import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.FunctionExpression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.expression.SymbolExpression;

public class ListEvaluator implements ExpressionEvaluator<ListExpression> {

  private ExpressionEvaluator<Expression> expressionEvaluator;

  private Expression evaluateFunction(FunctionExpression fn, List<Expression> argumentExpressions,
      Environment environment) {
    if (argumentExpressions.size() != fn.getArity()) {
      throw new ExpressionEvaluationException("Wrong number of arguments supplied to function");
    }

    Environment localEnvironment = new Environment(environment);

    int i = 0;
    for (String symbol : fn.getArgumentSymbols()) {
      localEnvironment.setDefinition(symbol, expressionEvaluator.evaluate(argumentExpressions.get(i++), environment));
    }

    return expressionEvaluator.evaluate(fn.getBody(), localEnvironment);
  }

  @Override
  public Expression evaluate(ListExpression input, Environment environment) {
    Objects.requireNonNull(expressionEvaluator, "ListEvaluator depends on ExpressionEvaluator<Expression>");

    ListExpression expr = (ListExpression) input;
    Expression firstExpr = expr.getChildren().get(0);

    if ((firstExpr instanceof AtomExpression) && !(firstExpr instanceof SymbolExpression)) {
      return input;
    }

    List<Expression> arguments = expr.getChildren().stream().skip(1).collect(toList());

    if (firstExpr instanceof FunctionExpression) {
      return evaluateFunction((FunctionExpression) firstExpr, arguments, environment);
    }

    String symbol = (String) ((SymbolExpression) firstExpr).getValue();
    Evaluable evaluable = environment.getDefinition(symbol)
        .orElseThrow(() -> new ExpressionEvaluationException("Undefined symbol '" + symbol + "'"));

    return (evaluable instanceof FunctionExpression)
        ? evaluateFunction((FunctionExpression) evaluable, arguments, environment)
        : ((Builtin) evaluable).evaluate(arguments, expressionEvaluator, environment);
  }

  public void setExpressionEvaluator(ExpressionEvaluator<Expression> expressionEvaluator) {
    this.expressionEvaluator = expressionEvaluator;
  }

}
