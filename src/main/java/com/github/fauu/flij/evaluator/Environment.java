package com.github.fauu.flij.evaluator;

import java.util.Map;
import java.util.Optional;

import com.github.fauu.flij.Evaluable;

import java.util.Collections;
import java.util.HashMap;

public class Environment {
  
  private Optional<Environment> parent;

  private final Map<String, Evaluable> definitions = new HashMap<>();
  private int numImplicitDefinitions = 0;
  
  public Environment() {
    this(null);
  }
  
  public Environment(Environment parent) {
    this.parent = Optional.ofNullable(parent);
  }
  
  public boolean hasDefinition(String symbol) {
    return definitions.containsKey(symbol) || parent.map(p -> p.hasDefinition(symbol)).orElse(false);
  }
  
  public Optional<Evaluable> getDefinition(String symbol) {
    Optional<Evaluable> localResult = Optional.ofNullable(definitions.get(symbol));

    return localResult.isPresent() ? localResult : parent.flatMap(p -> p.getDefinition(symbol));
  }
  
  public Map<String, Evaluable> getDefinitions() {
    return Collections.unmodifiableMap(definitions);
  }
  
  public void setDefinition(Evaluable evaluable) {
    String symbol = "$" + numImplicitDefinitions;
    setDefinition(symbol, evaluable);
    numImplicitDefinitions++;
  }
  
  public void setDefinition(String symbol, Evaluable evaluable) {
    definitions.put(symbol, evaluable);
  }

}
