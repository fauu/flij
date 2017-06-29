package com.github.fauu.flij.repl;

import java.util.Objects;
import java.util.Scanner;

import com.github.fauu.flij.Printer;
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
  public Printer printer;
  
  public Repl(Reader reader, Evaluator evaluator, Environment environment, Printer printer) {
    Objects.requireNonNull(reader, "REPL depends on a Reader");
    Objects.requireNonNull(evaluator, "REPL depends on a Evaluator");
    Objects.requireNonNull(environment, "REPL depends on an Environment");
    Objects.requireNonNull(printer, "REPL depends on a Printer");

    this.reader = reader;
    this.evaluator = evaluator;
    this.environment = environment;
    this.printer = printer;
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
        printer.print(evaluator.evaluate(reader.read(input, scanner), environment));
      } catch (ExpressionReadException|ExpressionEvaluationException e) {
        System.out.println(e.getMessage());
      }
    }

    scanner.close();
  }

}