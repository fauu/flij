package com.github.fauu.flij.reader.lexer;

import java.util.List;

public interface Lexer {

  List<Lexeme> lex(String input);

}