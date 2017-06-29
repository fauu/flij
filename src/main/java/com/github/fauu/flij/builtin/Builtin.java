package com.github.fauu.flij.builtin;

import java.util.List;
import java.util.function.Function;

import com.github.fauu.flij.Evaluable;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.WrongArgumentCountException;
import com.github.fauu.flij.evaluator.WrongArgumentTypeException;
import com.github.fauu.flij.expression.Expression;

public abstract class Builtin implements Evaluable {

  protected final String symbol;
  protected final String variant;
  protected final Function<Integer, Boolean> argumentCountValidator;
  
  protected Builtin(String symbol) {
    this(symbol, null, null);
  }

  protected Builtin(String symbol, Function<Integer, Boolean> argumentCountValidator) {
    this(symbol, null, argumentCountValidator);
  }

  protected Builtin(String symbol, String variant, Function<Integer, Boolean> argumentCountValidator) {
    this.symbol = symbol;
    this.variant = variant;
    this.argumentCountValidator = argumentCountValidator;
  }

  public abstract Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment);
  
  @SuppressWarnings("unchecked")
  protected final <T> T ensureArgumentType(Expression argument, Class<T> clazz) {
    if (!clazz.isInstance(argument)) {
      throw new WrongArgumentTypeException(symbol);
    }
    
    return (T) argument;
  }
  
  @SafeVarargs
  protected final Expression ensureArgumentType(Expression argument, Class<? extends Expression>... clazzes) {
    boolean match = false;
    for (Class<?> clazz : clazzes) {
      if (clazz.isInstance(argument)) {
        match = true;
      }
    }
    
    if (!match) {
      throw new WrongArgumentTypeException(symbol);
    }
    
    return argument;
  }

  protected final void validateArgumentCount(List<?> arguments) {
    if (!argumentCountValidator.apply(arguments.size())) {
      throw new WrongArgumentCountException(symbol);
    }
  }

  @Override
  public String toString() {
    return "<built-in>";
  }

}
