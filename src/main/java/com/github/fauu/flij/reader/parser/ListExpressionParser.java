package com.github.fauu.flij.reader.parser;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.expression.ListExpression;
import com.github.fauu.flij.reader.lexer.Lexeme;
import com.github.fauu.flij.reader.lexer.TokenType;

public class ListExpressionParser implements Parser<ListExpression> {

  private Parser<Expression> expressionParser;

  @Override
  public ListExpression parse(Iterator<Lexeme> it, Lexeme current) {
    Objects.requireNonNull(expressionParser);

    if (current.getTokenType() != TokenType.LIST_START) {
      throw new ExpressionParseException("List expression does not start with LIST_START token");
    }

    List<Expression> children = new LinkedList<>();

    while (it.hasNext()) {
      current = it.next();
      TokenType t = current.getTokenType();

      if (t == TokenType.LIST_END) {
        return new ListExpression(children);
      }

      children.add(expressionParser.parse(it, current));
    }

    throw new ExpressionParseException("List expression not closed");
  }

  public void setExpressionParser(Parser<Expression> expressionParser) {
    this.expressionParser = expressionParser;
  }

}
