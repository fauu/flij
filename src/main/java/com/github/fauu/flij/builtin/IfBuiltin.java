package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;

@RegisteredBuiltin("if")
@Shortcircuiting
public class IfBuiltin extends Builtin {

  public IfBuiltin(String symbol) {
    super(symbol);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n == 2 || n == 3;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    boolean evaluatedCondition = 
        BooleanExpression.fromExpression(evaluator.evaluate(arguments.get(0), environment)).getValue();

    int resultIdx = evaluatedCondition ? 1 : ((arguments.size() == 3) ? 2 : -1);
    Expression result = resultIdx > 0 ? arguments.get(resultIdx) : Expression.NIL;

    return evaluator.evaluate(result, environment);
  }

}
