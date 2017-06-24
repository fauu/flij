package com.github.fauu.flij.repl;

import java.util.Objects;
import java.util.Scanner;

import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.Evaluator;
import com.github.fauu.flij.evaluator.ExpressionEvaluationException;
import com.github.fauu.flij.reader.ExpressionReadException;
import com.github.fauu.flij.reader.Reader;

public class Repl {

  public static final String EXIT_COMMAND = "\\exit";
  public static final String LIST_COMMAND = "\\symbols";

  public Reader reader;
  public Evaluator evaluator;
  public Environment environment;

  public Repl(Reader reader, Evaluator evaluator, Environment environment) {
    Objects.requireNonNull(reader, "REPL depends on a Reader");
    Objects.requireNonNull(evaluator, "REPL depends on a Evaluator");
    Objects.requireNonNull(evaluator, "REPL depends on an Environment");

    this.reader = reader;
    this.evaluator = evaluator;
    this.environment = environment;
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

      if (input.equalsIgnoreCase(EXIT_COMMAND)) {
        break;
      }

      if (input.equalsIgnoreCase(LIST_COMMAND)) {
        System.out.println("Defined symbols:");
        environment.getDefinitions().forEach((s, expr) -> System.out.println(s + " : " + expr));
        continue;
      }

      try {
        System.out.println(evaluator.evaluate(reader.read(input), environment));
      } catch (ExpressionReadException|ExpressionEvaluationException e) {
        System.out.println(e.getMessage());
      }
    }

    scanner.close();
  }

}