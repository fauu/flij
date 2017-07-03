package com.github.fauu.flij.evaluator.environment;

import java.util.Map;
import java.util.Optional;

import com.github.fauu.flij.evaluator.Evaluable;

import java.util.Collections;
import java.util.HashMap;

public class DefaultEnvironment implements Environment {
  
  private Environment parent;

  private final Map<String, Evaluable> definitions = new HashMap<>();
  
  public DefaultEnvironment() {
    this(null);
  }
  
  public DefaultEnvironment(Environment parent) {
    this.parent = parent;
  }
  
  @Override
  public boolean hasDefinition(String symbol) {
    return definitions.containsKey(symbol) || (parent != null && parent.hasDefinition(symbol));
  }
  
  @Override
  public Optional<Evaluable> getDefinition(String symbol) {
    Evaluable definition = definitions.get(symbol);

    if (definition == null) {
      if (parent != null) {
        return parent.getDefinition(symbol);
      }
    }

    return Optional.ofNullable(definition);
  }
  
  @Override
  public Map<String, Evaluable> getDefinitions() {
    return Collections.unmodifiableMap(definitions);
  }
  
  @Override
  public void setDefinition(String symbol, Evaluable evaluable) {
    definitions.put(symbol, evaluable);
  }

}
