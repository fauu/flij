package com.github.fauu.flij.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

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

  public Expression read(String input) {
    Objects.requireNonNull(input, "No input provided for the reader");

    return parse(lex(input));
  }

  private Expression parse(List<Lexeme> lexemes) {
    Iterator<Lexeme> it = lexemes.iterator();
    Expression expr =  expressionParser.parse(it, it.next());
    
    if (it.hasNext()) {
      throw new ExpressionReadException("Single expression expected");
    }
    
    return expr;
  }
  
  private boolean isCharAtomEnding(char c) {
    return c == ' ' || TokenType.existsForChar(c);
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
        while (i < input.length() && !isCharAtomEnding(input.charAt(i))) {
          i++;
        }
        
        boolean matchedToken = false;

        String token = input.substring(start, i);
        for (TokenType tokenType : TokenType.atomicValues) {
          Matcher matcher = tokenType.getRegexPattern().matcher(token);
          if (matcher.find()) {
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