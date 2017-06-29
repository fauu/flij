package com.github.fauu.flij.reader.parser;

import java.util.Iterator;

import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.reader.lexeme.Lexeme;

public class AtomExpressionParser implements Parser<AtomExpression<?>> {

  @Override
  public AtomExpression<?> parse(Iterator<Lexeme> it, Lexeme current) {
    if (current.getTokenType().isPatternValue()) {
      return AtomExpression.fromLexeme(current);
    } else {
      throw new ExpressionParseException("Unexpected atom token");
    }
  }

}
