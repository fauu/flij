package com.github.fauu.flij.reader;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    Objects.requireNonNull(firstLine);

    return parse(lex(completeInput(firstLine, scanner)));
  }

  private String completeInput(String firstLine, Scanner scanner) {
    String completedInput = "";

    int listStartBias = 0;
    boolean beginning = true;
    do {
      String line = beginning ? firstLine : scanner.nextLine();
      listStartBias += determineListStartBiasForLine(line);
      completedInput += line.trim() + '\n';
      beginning = false;
    } while (listStartBias > 0);

    if (listStartBias < 0) {
      throw new ExpressionReadException("Unmatched list end delimiter");
    }

    return completedInput;
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
  
  private List<Lexeme> lex(String input) {
    List<Lexeme> output = new LinkedList<>();
    
    CharacterIterator it = new StringCharacterIterator(input);
    while (it.current() != CharacterIterator.DONE) {
      lexNext(it).ifPresent(output::add);
    }
    
    return output;
  }

  private Optional<Lexeme> lexNext(CharacterIterator it) {
    char c = it.current();
    
    if (TokenType.existsForChar(c)) {
      Lexeme lexeme = new Lexeme(TokenType.forChar(c));
      it.next();
      return Optional.of(lexeme);
    } else if (c == ' ' || c == '\n') {
      it.next();
    } else if (c == ';') {
      skipIfComment(it);
    } else {
      StringBuilder tokenBuilder = new StringBuilder();
      boolean inStringLiteral = false;
      while (c != CharacterIterator.DONE) {
        if (inStringLiteral) {
          if (c == '"') {
            tokenBuilder.append(c);
            c = it.next();
            break;
          }
        } else if (c == ' ' || c == '\n' || TokenType.existsForChar(c)) {
          break;
        }
        
        if (c == '"') {
          inStringLiteral = true;
        }
        
        tokenBuilder.append(c);
        c = it.next();
      }
      
      String token = tokenBuilder.toString();
      Optional<Lexeme> maybeLexeme = tryMatchingToken(token);
      Lexeme lexeme = maybeLexeme.orElseThrow(() -> new ExpressionReadException("Unexpected token '" + token + "'"));

      return Optional.of(lexeme);
    }

    return Optional.empty();
  }

  private void skipIfComment(CharacterIterator it) {
    if (it.next() == ';') {
      while (it.current() != '\n') {
        it.next();
      }
    } else {
      it.previous();
    }
  }

  private Optional<Lexeme> tryMatchingToken(String token) {
    for (TokenType tokenType : TokenType.patternValues) {
      Matcher matcher = tokenType.getPatternValue().matcher(token);

      if (matcher.find()) {
        return Optional.of(new Lexeme(tokenType, matcher.group()));
      }
    }

    return Optional.empty();
  }

}