package com.github.fauu.flij.repl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static java.util.stream.Collectors.toMap;

import com.github.fauu.flij.evaluator.Evaluator;
import com.github.fauu.flij.evaluator.ExpressionEvaluationException;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.printer.Printer;
import com.github.fauu.flij.reader.ExpressionReadException;
import com.github.fauu.flij.reader.Reader;
import com.github.fauu.flij.repl.command.ExitCommand;
import com.github.fauu.flij.repl.command.HelpCommand;
import com.github.fauu.flij.repl.command.ListSymbolsCommand;
import com.github.fauu.flij.repl.command.ReplCommand;

public class Repl {

  private static final String COMMAND_PREFIX = "\\";

  public Reader reader;
  public Evaluator evaluator;
  public Environment environment;
  public Printer printer;

  private Map<String, ReplCommand> commands = new HashMap<>();

  public Repl(Reader reader, Evaluator evaluator, Environment environment, Printer printer) {
    Objects.requireNonNull(reader);
    Objects.requireNonNull(evaluator);
    Objects.requireNonNull(environment);
    Objects.requireNonNull(printer);

    this.reader = reader;
    this.evaluator = evaluator;
    this.environment = environment;
    this.printer = printer;

    registerCommands();
  }

  private void registerCommands() {
    commands.put("exit", new ExitCommand());
    commands.put("symbols", new ListSymbolsCommand());
    commands.put("help", new HelpCommand(COMMAND_PREFIX));

    Map<String, String> helpEntries = commands.entrySet()
        .stream()
        .collect(toMap(Map.Entry::getKey, entry -> entry.getValue().getHelpEntry()));
    
    ((HelpCommand) commands.get("help")).setCommandEntries(helpEntries);
  }

  public void run() {
    System.out.println("flij REPL (type \\exit to exit)");

    Scanner scanner = new Scanner(System.in);

    for (;;) {
      System.out.print("> ");

      String input = scanner.nextLine().trim();

      if (input.isEmpty()) {
        continue;
      }

      if (input.startsWith(COMMAND_PREFIX)) {
        tryDispatchingCommand(input.substring(COMMAND_PREFIX.length()), environment);
      } else {
        try {
          printer.print(evaluator.evaluate(reader.read(input, scanner), environment));
        } catch (ExpressionReadException | ExpressionEvaluationException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  private void tryDispatchingCommand(String input, Environment globalEnvironment) {
    String[] tokens = input.split(" ");
    String keyword = tokens[0];

    ReplCommand command = commands.get(keyword);
    if (command != null) {
      command.execute(tokens, globalEnvironment);
    } else {
      System.out.println("Unrecognized REPL command '" + keyword + "'");
    }
  }

}