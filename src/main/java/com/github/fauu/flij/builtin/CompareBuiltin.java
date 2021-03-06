package com.github.fauu.flij.builtin;

import java.util.List;

import static java.util.stream.Collectors.toList;

import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.NumberExpression;

@RegisteredVariantBuiltin({
  @SymbolMapping(symbol="<", variant="LESS_THAN"),
  @SymbolMapping(symbol="<=", variant="LESS_THAN_OR_EQUAL_TO"),
  @SymbolMapping(symbol=">", variant="MORE_THAN"),
  @SymbolMapping(symbol=">=", variant="MORE_THAN_OR_EQUAL_TO"),
})
public class CompareBuiltin extends Builtin {

  public CompareBuiltin(String symbol, String variant) {
    super(symbol, variant);
  }

  @Override
  public boolean isArgumentCountValid(int n) {
    return n >= 1;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    List<Double> numberArguments = arguments.stream()
        .map(arg -> ensureArgumentType(arg, NumberExpression.class).getValue())
        .collect(toList());

    boolean result = true;
    for (int i = 0; i < numberArguments.size() - 1; i++) {
      double a = numberArguments.get(i);
      double b = numberArguments.get(i + 1);
      
      boolean partialResult;
      switch (variant) {
        case "LESS_THAN":
          partialResult = a < b;
          break;
        case "LESS_THAN_OR_EQUAL_TO":
          partialResult = a <= b;
          break;
        case "MORE_THAN":
          partialResult = a > b;
          break;
        case "MORE_THAN_OR_EQUAL_TO":
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
