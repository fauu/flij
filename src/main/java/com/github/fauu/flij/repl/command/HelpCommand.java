package com.github.fauu.flij.repl.command;

import java.util.Map;
import java.util.Objects;

import com.github.fauu.flij.evaluator.environment.Environment;

public class HelpCommand extends ReplCommand {
  
  private static final String HEADER_TEXT = String.join("\n",
      "Enter flij expressions to get them evaluated\n",
      "Available special commands:"
      );
  
  private String commandPrefix;
  private Map<String, String> commandEntries;

  public HelpCommand(String commandPrefix) {
    this.commandPrefix = commandPrefix;
  }
  
  @Override
  public void execute(String[] args, Environment globalEnvironment) {
    Objects.requireNonNull(commandEntries);
    
    System.out.println(HEADER_TEXT);
    commandEntries.forEach((keyword, helpEntry) -> System.out.println(commandPrefix + keyword + " - " + helpEntry));
  }
  
  public String getHelpEntry() {
    return "displays this message";
  }

  public void setCommandEntries(Map<String, String> commandEntries) {
    this.commandEntries = commandEntries;
  }

}
