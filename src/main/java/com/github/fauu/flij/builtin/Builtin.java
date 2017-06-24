package com.github.fauu.flij.builtin;

import java.util.List;
import java.util.function.Function;

import com.github.fauu.flij.Evaluable;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;

public abstract class Builtin implements Evaluable {

  protected final String symbol;
  protected final Function<Integer, Boolean> argumentCountValidator;

  protected Builtin(String symbol) {
    this(symbol, null);
  }

  protected Builtin(String symbol, Function<Integer, Boolean> argumentCountValidator) {
    this.symbol = symbol;
    this.argumentCountValidator = argumentCountValidator;
  }

  public abstract Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment);

  public final String getSymbol() {
    return symbol;
  }
  
  @SuppressWarnings("unchecked")
  protected final <T> T ensureArgumentType(Expression argument, Class<T> clazz) {
    if (!clazz.isInstance(argument)) {
      throw new IllegalArgumentException("Wrong type of arguments passed to " + symbol);
    }
    
    return (T) argument;
  }

  protected final void validateArgumentCount(List<?> arguments) {
    if (!argumentCountValidator.apply(arguments.size())) {
      throw new IllegalArgumentException("Wrong number of arguments passed to " + symbol);
    }
  }

  @Override
  public String toString() {
    return "<builtin>";
  }

}
