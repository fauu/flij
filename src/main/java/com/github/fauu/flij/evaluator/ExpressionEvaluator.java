package com.github.fauu.flij.evaluator;

import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;

public interface ExpressionEvaluator<T extends Expression> {
  
  Expression evaluate(T input, Environment environment);

}
