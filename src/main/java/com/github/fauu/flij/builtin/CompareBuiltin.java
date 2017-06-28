package com.github.fauu.flij.builtin;

import java.util.List;

import static java.util.stream.Collectors.toList;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

public class CompareBuiltin extends Builtin {

  public enum Variant {
    LESS_THAN,
    LESS_THAN_OR_EQUAL_TO,
    MORE_THAN,
    MORE_THAN_OR_EQUAL_TO;
  }

  private Variant variant;

  public CompareBuiltin(String symbol, Variant variant) {
    super(symbol, n -> n >= 1);

    this.variant = variant;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    List<Float> numberArguments = arguments.stream()
        .map(arg -> ensureArgumentType(arg, NumberExpression.class).getValue()).collect(toList());

    boolean result = true;
    for (int i = 0; i < numberArguments.size() - 1; i++) {
      float a = numberArguments.get(i);
      float b = numberArguments.get(i + 1);
      
      boolean partialResult;
      switch (variant) {
        case LESS_THAN:
          partialResult = a < b;
          break;
        case LESS_THAN_OR_EQUAL_TO:
          partialResult = a <= b;
          break;
        case MORE_THAN:
          partialResult = a > b;
          break;
        case MORE_THAN_OR_EQUAL_TO:
          partialResult = a >= b;
          break;
        default:
          partialResult = true;
      }
      
      if (!partialResult) {
        result = false;
        break;
      }
    }
    
    return new BooleanExpression(result);
  }

}
