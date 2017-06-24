package com.github.fauu.flij.reader.parser;

import java.util.Iterator;
import java.util.Objects;

import com.github.fauu.flij.expression.AtomExpression;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.reader.lexeme.Lexeme;
import com.github.fauu.flij.reader.lexeme.TokenType;

public class ExpressionParser implements Parser<Expression> {

  private Parser<ListExpression> listParser;
  private Parser<AtomExpression<?>> atomParser;

  @Override
  public Expression parse(Iterator<Lexeme> it, Lexeme current) {
    Objects.requireNonNull(listParser, "ExpressionParser depends on a Parser<ListExpression>");
    Objects.requireNonNull(atomParser, "ExpressionParser depends on a Parser<AtomExpression>");
    
    TokenType t = current.getTokenType();
    
    Parser<? extends Expression> delegate = null;
    if (t == TokenType.LIST_START) {
      delegate = listParser;
    } else if (t.isAtomic()) {
      delegate = atomParser;
    }
    
    if (delegate != null) {
      return delegate.parse(it, current);
    }

    throw new ExpressionParseException("Unexpected token '" + t + "'");
  }

  public void setListParser(Parser<ListExpression> listParser) {
    this.listParser = listParser;
  }

  public void setAtomParser(Parser<AtomExpression<?>> atomParser) {
    this.atomParser = atomParser;
  }

}
