package com.github.fauu.flij.evaluator.environment;

import java.util.Map;
import java.util.Optional;

import com.github.fauu.flij.evaluator.Evaluable;

public interface Environment {

  boolean hasDefinition(String symbol);

  Optional<Evaluable> getDefinition(String symbol);

  Map<String, Evaluable> getDefinitions();

  void setDefinition(Evaluable evaluable);

  void setDefinition(String symbol, Evaluable evaluable);

}