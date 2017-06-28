package com.github.fauu.flij.builtin;

import java.util.List;

import static java.util.stream.Collectors.joining;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;

public class PrintBuiltin extends Builtin {
  
  public enum Variant {
    NEWLINE,
    NO_NEWLINE;
  }
  
  private Variant variant;
  
  public PrintBuiltin(Variant variant, String symbol) {
    super(symbol, n -> n >= 1);
    
    this.variant = variant;
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    String output = arguments.stream().map(arg ->
      evaluator.evaluate(arg, environment).toString()
    ).collect(joining(" "));
    
    System.out.print(output);
    
    if (variant == Variant.NEWLINE) {
      System.out.print(System.lineSeparator());
    }

    return Expression.NIL;
  }

}
