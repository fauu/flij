package com.github.fauu.flij.evaluator;

import java.util.Objects;

import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;

public class Evaluator implements ExpressionEvaluator<Expression> {
  
  private ExpressionEvaluator<AtomExpression<?>> atomEvaluator;
  private ExpressionEvaluator<ListExpression> listEvaluator;

  public Expression evaluate(Expression input, Environment environment) {
    Objects.requireNonNull(atomEvaluator);
    Objects.requireNonNull(listEvaluator);
    
    if (input instanceof AtomExpression) {
      return atomEvaluator.evaluate((AtomExpression<?>) input, environment);
    } else if (input instanceof ListExpression) {
      return listEvaluator.evaluate((ListExpression) input, environment);
    } else {
      return Expression.NIL;
    }
  }
  
  public void setAtomEvaluator(ExpressionEvaluator<AtomExpression<?>> atomEvaluator) {
    this.atomEvaluator = atomEvaluator;
  }

  public void setListEvaluator(ExpressionEvaluator<ListExpression> listEvaluator) {
    this.listEvaluator = listEvaluator;
  }

}
