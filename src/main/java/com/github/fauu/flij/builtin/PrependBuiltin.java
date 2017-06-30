package com.github.fauu.flij.builtin;

import java.util.LinkedList;
import java.util.List;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.expression.CharacterExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.expression.StringExpression;

@RegisteredBuiltin("prepend")
public class PrependBuiltin extends Builtin {

  public PrependBuiltin(String symbol) {
    super(symbol, n -> n == 2);
  }

  @Override
  public Expression evaluate(List<Expression> arguments, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    validateArgumentCount(arguments);

    Expression element = evaluator.evaluate(arguments.get(0), environment);
    Expression evaluatedListArgument = evaluator.evaluate(arguments.get(1), environment);

    if (evaluatedListArgument instanceof StringExpression) {
      CharacterExpression character = ensureArgumentType(element, CharacterExpression.class);
      return new StringExpression(character.getValue() + ((StringExpression) evaluatedListArgument).getValue());
    }
    
    ListExpression list = ensureArgumentType(evaluatedListArgument, ListExpression.class);
    
    List<Expression> newElements = new LinkedList<Expression>();
    newElements.add(element);
    newElements.addAll(list.getElements());

    return new ListExpression(newElements);
  }

}
