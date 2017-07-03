package com.github.fauu.flij.reader.lexer;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import com.github.fauu.flij.reader.ExpressionReadException;
import com.github.fauu.flij.reader.lexer.Lexeme;
import com.github.fauu.flij.reader.lexer.TokenType;

public class DefaultLexer implements Lexer {

  @Override
  public List<Lexeme> lex(String input) {
    List<Lexeme> output = new LinkedList<>();
    
    CharacterIterator it = new StringCharacterIterator(input);
    while (it.current() != CharacterIterator.DONE) {
      nextLexeme(it).ifPresent(output::add);
    }
    
    return output;
  }

  private Optional<Lexeme> nextLexeme(CharacterIterator it) {
    char c = it.current();
    
    if (TokenType.existsForChar(c)) {
      Lexeme lexeme = new Lexeme(TokenType.forChar(c));
      it.next();
      return Optional.of(lexeme);
    } else if (charSeparatesTokens(c)) {
      it.next();
    } else if (c == ';') {
      skipIfComment(it);
    } else {
      StringBuilder tokenBuilder = new StringBuilder();
      boolean inStringLiteral = false;
      while (c != CharacterIterator.DONE) {
        if (inStringLiteral) {
          if (charDelimitsStringLiteral(c)) {
            tokenBuilder.append(c);
            it.next();
            break;
          }
        } else if (charSeparatesTokens(c) || TokenType.existsForChar(c)) {
          break;
        }
        
        if (charDelimitsStringLiteral(c)) {
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

  private boolean charDelimitsStringLiteral(char c) {
    return c == '"';
  }

  private boolean charSeparatesTokens(char c) {
    return c == ' ' || c == '\n';
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
