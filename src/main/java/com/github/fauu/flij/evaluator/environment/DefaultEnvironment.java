package com.github.fauu.flij.evaluator.environment;

import java.util.Map;
import java.util.Optional;

import com.github.fauu.flij.evaluator.Evaluable;

import java.util.Collections;
import java.util.HashMap;

public class DefaultEnvironment implements Environment {
  
  private Optional<Environment> parent;

  private final Map<String, Evaluable> definitions = new HashMap<>();
  private int numImplicitDefinitions = 0;
  
  public DefaultEnvironment() {
    this(null);
  }
  
  public DefaultEnvironment(Environment parent) {
    this.parent = Optional.ofNullable(parent);
  }
  
  @Override
  public boolean hasDefinition(String symbol) {
    return definitions.containsKey(symbol) || parent.map(p -> p.hasDefinition(symbol)).orElse(false);
  }
  
  @Override
  public Optional<Evaluable> getDefinition(String symbol) {
    Optional<Evaluable> localResult = Optional.ofNullable(definitions.get(symbol));

    return localResult.isPresent() ? localResult : parent.flatMap(p -> p.getDefinition(symbol));
  }
  
  @Override
  public Map<String, Evaluable> getDefinitions() {
    return Collections.unmodifiableMap(definitions);
  }
  
  @Override
  public void setDefinition(Evaluable evaluable) {
    String symbol = "$" + numImplicitDefinitions;
    setDefinition(symbol, evaluable);
    numImplicitDefinitions++;
  }
  
  @Override
  public void setDefinition(String symbol, Evaluable evaluable) {
    definitions.put(symbol, evaluable);
  }

}
