package com.github.fauu.flij.reader;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.reader.lexer.Lexeme;
import com.github.fauu.flij.reader.lexer.Lexer;
import com.github.fauu.flij.reader.lexer.TokenType;
import com.github.fauu.flij.reader.parser.AtomExpressionParser;
import com.github.fauu.flij.reader.parser.ExpressionParser;
import com.github.fauu.flij.reader.parser.ListExpressionParser;

public class DefaultReader implements Reader {

  private final Lexer lexer;
  private final ExpressionParser expressionParser;

  public DefaultReader(Lexer lexer) {
    this.lexer = lexer;
    
    // XXX: inject this?
    expressionParser = new ExpressionParser();

    ListExpressionParser listParser = new ListExpressionParser();
    listParser.setExpressionParser(expressionParser);

    AtomExpressionParser atomParser = new AtomExpressionParser();

    expressionParser.setListParser(listParser);
    expressionParser.setAtomParser(atomParser);
  }

  @Override
  public Expression read(String firstLine, Scanner scanner) {
    Objects.requireNonNull(lexer);
    Objects.requireNonNull(firstLine);
    Objects.requireNonNull(scanner);

    return parse(lexer.lex(completeInput(firstLine, scanner)));
  }

  private String completeInput(String firstLine, Scanner scanner) {
    StringBuilder completedInputBuilder = new StringBuilder();

    int listStartBias = 0;
    boolean beginning = true;
    do {
      String line = beginning ? firstLine : scanner.nextLine();
      listStartBias += determineListStartBiasForLine(line);
      completedInputBuilder.append(line.trim()).append('\n');
      beginning = false;
    } while (listStartBias > 0);

    if (listStartBias < 0) {
      throw new ExpressionReadException("Unmatched list end delimiter");
    }

    return completedInputBuilder.toString();
  }

  private int determineListStartBiasForLine(String line) {
    int listStartBias = 0;
    
    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);

      if (c == TokenType.LIST_START.getCharacterValue()) {
        listStartBias++;
      } else if (c == TokenType.LIST_END.getCharacterValue()) {
        listStartBias--;
      }
    }

    return listStartBias;
  }

  private Expression parse(List<Lexeme> lexemes) {
    if (lexemes.size() == 0) {
      return Expression.NIL;
    }

    Iterator<Lexeme> it = lexemes.iterator();
    Expression expr = expressionParser.parse(it, it.next());

    if (it.hasNext()) {
      throw new ExpressionReadException("Single expression expected");
    }

    return expr;
  }
  

}