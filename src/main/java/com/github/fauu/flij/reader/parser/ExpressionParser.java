package com.github.fauu.flij.reader.parser;

import java.util.Iterator;
import java.util.Objects;

import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.expression.SymbolExpression;
import com.github.fauu.flij.reader.lexer.Lexeme;
import com.github.fauu.flij.reader.lexer.TokenType;

public class ExpressionParser implements Parser<Expression> {

  private Parser<ListExpression> listParser;
  private Parser<AtomExpression<?>> atomParser;

  @Override
  public Expression parse(Iterator<Lexeme> it, Lexeme current) {
    Objects.requireNonNull(listParser);
    Objects.requireNonNull(atomParser);
    
    boolean quoted = false;
    if (current.getTokenType() == TokenType.QUOTE) {
      quoted = true;
      current = it.next();
    }
    
    TokenType t = current.getTokenType();
    
    Expression parsedExpression = pickDelegateParser(t).parse(it, current);
      
    if (quoted) {
      parsedExpression = new ListExpression(new SymbolExpression("quote"), parsedExpression);
    }
    
    return parsedExpression;
  }

  private Parser<? extends Expression> pickDelegateParser(TokenType t) {
    if (t == TokenType.LIST_START) {
      return listParser;
    } else if (t.isPatternValue()) {
      return atomParser;
    }

    throw new ExpressionParseException("Unexpected token " + t);
  }

  public void setListParser(Parser<ListExpression> listParser) {
    this.listParser = listParser;
  }

  public void setAtomParser(Parser<AtomExpression<?>> atomParser) {
    this.atomParser = atomParser;
  }

}
