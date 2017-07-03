package com.github.fauu.flij.repl.command;

import java.util.Map;
import java.util.Objects;

import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.repl.Repl;

public class HelpCommand extends ReplCommand {
  
  private static final String HEADER_TEXT = String.join("\n",
      "Enter flij expressions to get them evaluated\n",
      "Available special commands:"
      );
  
  private Map<String, String> commandEntries;

  @Override
  public void execute(String[] args, Environment globalEnvironment) {
    Objects.requireNonNull(commandEntries);
    
    System.out.println(HEADER_TEXT);
    commandEntries.forEach((keyword, entry) -> System.out.printf("%s%s - %s\n", Repl.COMMAND_PREFIX, keyword, entry));
  }
  
  public String getHelpEntry() {
    return "displays this message";
  }

  public void setCommandEntries(Map<String, String> commandEntries) {
    this.commandEntries = commandEntries;
  }

}
