package com.github.fauu.flij.builtin;

import java.util.List;
import java.util.function.Function;

import com.github.fauu.flij.Evaluable;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;

public abstract class Builtin implements Evaluable {

  protected final String symbol;

  protected Builtin(String symbol) {
    this.symbol = symbol;
  }

  public abstract Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment);

  public final String getSymbol() {
    return symbol;
  }
  
  protected final void validateArgumentCount(List<?> arguments, Function<Integer, Boolean> validator) {
    if (!validator.apply(arguments.size())) {
      throw new IllegalArgumentException("Wrong number of arguments passed to " + symbol);
    }
  }
  
  protected final void rejectArgumentType() {
    throw new IllegalArgumentException("Wrong type of arguments passed to " + symbol);
  }

  @Override
  public String toString() {
    return "<builtin>";
  }

}
