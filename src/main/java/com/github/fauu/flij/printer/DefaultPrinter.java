package com.github.fauu.flij.printer;

import com.github.fauu.flij.expression.Expression;

public class DefaultPrinter implements Printer {
  
  @Override
  public void print(Expression expression) { 
    System.out.println(expression.toPrinterString());
  }

}
