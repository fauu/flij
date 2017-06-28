package com.github.fauu.flij.expression;

public class QuotedExpression extends Expression {
  
  private Expression value;
  
  public QuotedExpression(Expression value) {
    this.value = value;
  }
  
  public Expression getValue() {
    return value;
  }

}
