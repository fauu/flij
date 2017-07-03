package com.github.fauu.flij.reader.parser;

import java.util.Iterator;

import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.expression.BooleanExpression;
import com.github.fauu.flij.expression.CharacterExpression;
import com.github.fauu.flij.expression.NumberExpression;
import com.github.fauu.flij.expression.StringExpression;
import com.github.fauu.flij.expression.SymbolExpression;
import com.github.fauu.flij.reader.lexer.Lexeme;

public class AtomExpressionParser implements Parser<AtomExpression<?>> {
  
  @Override
  public AtomExpression<?> parse(Iterator<Lexeme> it, Lexeme current) {
    if (current.getTokenType().isPatternValue()) {
      return makeAtomExpressionFromLexeme(current);
    }

    throw new ExpressionParseException("Unexpected atom token");
  }
  
  private AtomExpression<?> makeAtomExpressionFromLexeme(Lexeme lexeme) {
    switch (lexeme.getTokenType()) {
      case NUMBER:
        return new NumberExpression(lexeme);
      case BOOLEAN:
        return new BooleanExpression(lexeme);
      case CHARACTER:
        return new CharacterExpression(lexeme);
      case STRING:
        return new StringExpression(lexeme);
      case SYMBOL:
        return new SymbolExpression(lexeme);
      default:
        throw new IllegalArgumentException("Cannot construct atom expression from given token");
    }
  }
  
}
