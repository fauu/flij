package com.github.fauu.flij.expression;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ListExpression extends Expression implements SequenceExpression<ListExpression, Expression> {

  private List<Expression> children;
  
  public ListExpression() {
    this.children = Collections.emptyList();
  }
  
  public ListExpression(Expression first, Expression second) {
    this(new LinkedList<Expression>(Arrays.asList(first, second)));
  }
  
  public ListExpression(List<Expression> children) {
    this.children = Collections.unmodifiableList(children);
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
  public List<Expression> getElements() {
    return children;
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
    if (children.size() == 0) {
      return "nil";
    }

    StringBuilder builder = new StringBuilder();

    builder.append('(');
    IntStream.range(0, children.size()).forEach(idx -> {
      builder.append(children.get(idx));

      if (idx < children.size() - 1) {
        builder.append(' ');
      }
    });
    builder.append(')');
    
    return builder.toString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(children);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    } else if (!(obj instanceof ListExpression)) {
      return false;
    } else {
      ListExpression other = (ListExpression) obj;
      
      return getElements().equals(other.getElements());
    }
  }

}