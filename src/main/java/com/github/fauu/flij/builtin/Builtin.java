package com.github.fauu.flij.builtin;

import java.util.List;

import com.github.fauu.flij.Evaluable;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.WrongArgumentTypeException;
import com.github.fauu.flij.expression.Expression;

public abstract class Builtin implements Evaluable {

  protected final String variant;

  private final String symbol;
  
  protected Builtin(String symbol) {
    this(symbol, null);
  }

  protected Builtin(String symbol, String variant) {
    this.symbol = symbol;
    this.variant = variant;
  }
  
  public boolean isArgumentCountValid(int n) {
    return true;
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

  public String getSymbol() {
    return symbol;
  }
  
  @Override
  public String toString() {
    return "<built-in>";
  }

}
