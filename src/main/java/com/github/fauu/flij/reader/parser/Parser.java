package com.github.fauu.flij.reader.parser;

import java.util.Iterator;

import com.github.fauu.flij.reader.lexer.Lexeme;

interface Parser<T> {
  
  T parse(Iterator<Lexeme> it, Lexeme current);

}
