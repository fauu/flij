package com.github.fauu.flij.reader;

import java.util.Scanner;

import com.github.fauu.flij.expression.Expression;

public interface Reader {

  Expression read(String firstLine, Scanner scanner);

}