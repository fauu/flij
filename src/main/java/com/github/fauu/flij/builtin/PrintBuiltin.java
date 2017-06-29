package com.github.fauu.flij.builtin;

import java.util.List;

import static java.util.stream.Collectors.joining;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.Expression;

@RegisteredVariantBuiltin({
  @SymbolMapping(symbol="put", variant="NO_NEWLINE"),
  @SymbolMapping(symbol="putln", variant="NEWLINE")
})
public class PrintBuiltin extends Builtin {
  
  public PrintBuiltin(String symbol, String variant) {
    super(symbol, variant, n -> n >= 1);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    String output = arguments.stream().map(arg ->
      evaluator.evaluate(arg, environment).toString()
    ).collect(joining(" "));
    
    System.out.print(output);
    
    if (variant.equals("NEWLINE")) {
      System.out.print(System.lineSeparator());
    }

    return Expression.NIL;
  }

}
