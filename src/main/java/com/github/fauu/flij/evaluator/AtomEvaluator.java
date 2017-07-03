package com.github.fauu.flij.evaluator;

import java.util.Objects;
import java.util.Optional;

import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.SymbolExpression;

public class AtomEvaluator implements ExpressionEvaluator<AtomExpression<?>> {

  private ExpressionEvaluator<Expression> expressionEvaluator;

  @Override
  public Expression evaluate(AtomExpression<?> input, Environment environment) {
    Objects.requireNonNull(expressionEvaluator);

    if (input instanceof SymbolExpression) {
      Optional<Evaluable> maybeEvaluable = environment.getDefinition(((SymbolExpression) input).getValue());
      if (maybeEvaluable.isPresent()) {
        Evaluable evaluable = maybeEvaluable.get();
        if (evaluable instanceof Expression) {
          return (Expression) evaluable;
        }
      }
    }

    environment.setDefinition(input);
    return input;
  }

  public void setExpressionEvaluator(ExpressionEvaluator<Expression> expressionEvaluator) {
    this.expressionEvaluator = expressionEvaluator;
  }

}
