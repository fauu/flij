package com.github.fauu.flij;

import com.github.fauu.flij.expression.Expression;

public class Printer {
  
  public void print(Expression expression) { 
    System.out.println(expression.toPrinterString());
  }

}
