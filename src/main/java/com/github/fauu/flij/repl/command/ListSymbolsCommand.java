package com.github.fauu.flij.repl.command;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

import com.github.fauu.flij.evaluator.environment.Environment;

public class ListSymbolsCommand extends ReplCommand {
  
  @Override
  public void execute(String[] args, Environment globalEnvironment) {
    System.out.println("Defined symbols:");

    globalEnvironment.getDefinitions()
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByKey())
      .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> oldVal, LinkedHashMap::new))
      .forEach((s, expr) -> System.out.println(s + " : " + expr));
  }
  
  public String getHelpEntry() {
    return "lists symbols defined in the global environment";
  }

}
