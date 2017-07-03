package com.github.fauu.flij.repl.command;

import com.github.fauu.flij.evaluator.environment.Environment;

public class ExitCommand extends ReplCommand {
  
  @Override
  public void execute(String[] args, Environment globalEnvironment) {
    System.exit(0);
  }

  public String getHelpEntry() {
    return "exits the program";
  }

}
