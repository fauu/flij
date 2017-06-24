package com.github.fauu.flij.expression;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ListExpression extends Expression {

  private List<Expression> children;
  
  public ListExpression(List<Expression> children) {
    this.children = Collections.unmodifiableList(children);
  }
  
  public List<Expression> getChildren() {
    return children;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append('(');
    IntStream.range(0, children.size()).forEach(idx -> {
      if (idx > 0) {
        builder.append(' ');
      }

      builder.append(children.get(idx));

      if (idx < children.size() - 1) {
        builder.append(' ');
      }
    });
    builder.append(')');
    
    return builder.toString();
  }

}