package com.github.fauu.flij.expression;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ListExpression extends Expression implements SequenceExpression<ListExpression, Expression> {

  private List<Expression> children;
  
  public ListExpression(List<Expression> children) {
    this.children = Collections.unmodifiableList(children);
  }
  
  public List<Expression> getChildren() {
    return children;
  }

  @Override
  public int getLength() {
    return children.size();
  }

  @Override
  public Expression getElement(int idx) {
    return children.get(idx);
  }

  @Override
  public ListExpression subSequence(int fromIdx, int toIdx) {
    return new ListExpression(children.subList(fromIdx, toIdx));
  }

  @Override
  public ListExpression reversed() {
    List<Expression> childrenCopy = new LinkedList<>(children);
    Collections.reverse(childrenCopy);

    return new ListExpression(childrenCopy);
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