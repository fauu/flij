package com.github.fauu.flij.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.Scanner;

import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.reader.lexeme.Lexeme;
import com.github.fauu.flij.reader.lexeme.TokenType;
import com.github.fauu.flij.reader.parser.AtomExpressionParser;
import com.github.fauu.flij.reader.parser.ExpressionParser;
import com.github.fauu.flij.reader.parser.ListExpressionParser;

public class Reader {

  private ExpressionParser expressionParser;

  public Reader() {
    expressionParser = new ExpressionParser();

    ListExpressionParser listParser = new ListExpressionParser();
    listParser.setExpressionParser(expressionParser);

    AtomExpressionParser atomParser = new AtomExpressionParser();

    expressionParser.setListParser(listParser);
    expressionParser.setAtomParser(atomParser);
  }

  public Expression read(String firstLine, Scanner scanner) {
    Objects.requireNonNull(firstLine, "No input provided for the reader");
    
    return parse(lex(completeInput(firstLine, scanner)));
  }
  
  private String completeInput(String firstLine, Scanner scanner) {
    String completedInput = "";

    int listStartBias = 0;
    do {
      String input = listStartBias == 0 ? firstLine : scanner.nextLine();

      for (int i = 0; i < input.length(); i++) {
        char c = input.charAt(i);
        
        if (c == TokenType.LIST_START.getCharacterValue()) {
          listStartBias++;
        } else if (c == TokenType.LIST_END.getCharacterValue()) {
          listStartBias--;
        }
      }

      completedInput += input.trim() + ' ';
    } while (listStartBias > 0);
    
    if (listStartBias < 0) {
      throw new ExpressionReadException("Unmatched list end delimiter");
    }
    
    return completedInput;
  }

  private Expression parse(List<Lexeme> lexemes) {
    if (lexemes.size() == 0) {
      return Expression.NIL;
    }

    Iterator<Lexeme> it = lexemes.iterator();
    Expression expr =  expressionParser.parse(it, it.next());
    
    if (it.hasNext()) {
      throw new ExpressionReadException("Single expression expected");
    }
    
    return expr;
  }
  
  private boolean isCharAtomEnding(char c, boolean inStringLiteral) {
    return (!inStringLiteral && c == ' ') || TokenType.existsForChar(c);
  }

  private List<Lexeme> lex(String input) {
    List<Lexeme> output = new ArrayList<>();

    for (int i = 0; i < input.length();) {
      final char c = input.charAt(i);
      
      if (TokenType.existsForChar(c)) {
        output.add(new Lexeme(TokenType.forChar(c)));
        i++;
      } else if (c == ' ') {
        i++;
      } else {
        int start = i;
        boolean inStringLiteral = false;
        while (i < input.length()) {
          char cc = input.charAt(i);

          if (cc == '"') {
            inStringLiteral = true;
          }

          if (isCharAtomEnding(cc, inStringLiteral)) {
            break;
          }

          i++;
        }
        
        boolean matchedToken = false;

        String token = input.substring(start, i);
        for (TokenType tokenType : TokenType.patternValues) {
          Matcher matcher = tokenType.getPatternValue().matcher(token);
          if (matcher.find()) {
            if (tokenType == TokenType.COMMENT_START) {
              return output;
            }

            output.add(new Lexeme(tokenType, matcher.group()));
            matchedToken = true;
            break;
          }
        }

        if (!matchedToken) {
          throw new ExpressionReadException("Unexpected token '" + token + "'");
        }
      }
    }

    return output;
  }

}