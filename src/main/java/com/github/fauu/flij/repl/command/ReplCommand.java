package com.github.fauu.flij.repl.command;

import com.github.fauu.flij.evaluator.environment.Environment;

public abstract class ReplCommand {
  
  public abstract void execute(String[] args, Environment globalEnvironment);
  
  public abstract String getHelpEntry();

}
