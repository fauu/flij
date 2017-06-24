package com.github.fauu.flij.expression;

import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class FunctionExpression extends Expression {
  
  private List<String> argumentSymbols;
  private Expression body;
  
  public FunctionExpression(List<String> argumentSymbols, Expression body) {
    this.argumentSymbols = new LinkedList<>(argumentSymbols);
    this.body = body;
  }
  
  @Override
  public String toString() {
    return "<" + argumentSymbols.size() + "-ary function>";
  }
  
  public int getArity() {
    return argumentSymbols.size();
  }
  
  public List<String> getArgumentSymbols() {
    return Collections.unmodifiableList(argumentSymbols);
  }
  
  public Expression getBody() {
    return body;
  }

}
