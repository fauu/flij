package com.github.fauu.flij;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import com.github.fauu.flij.builtin.Builtin;
import com.github.fauu.flij.builtin.SymbolMapping;
import com.github.fauu.flij.builtin.RegisteredBuiltin;
import com.github.fauu.flij.builtin.RegisteredVariantBuiltin;
import com.github.fauu.flij.evaluator.AtomEvaluator;
import com.github.fauu.flij.evaluator.Evaluator;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.ListEvaluator;
import com.github.fauu.flij.evaluator.environment.DefaultEnvironment;
import com.github.fauu.flij.evaluator.environment.Environment;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.printer.DefaultPrinter;
import com.github.fauu.flij.reader.Reader;
import com.github.fauu.flij.reader.lexer.DefaultLexer;
import com.github.fauu.flij.repl.Repl;
import com.github.fauu.flij.reader.DefaultReader;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

class Flij {

  private static final String STANDARD_LIBRARY_PATH = "/lib/std.flj";
  private static final String BUILTINS_PACKAGE_NAME = "com.github.fauu.flij.builtin";

  void run() {
    Reader reader = new DefaultReader(new DefaultLexer());
    Evaluator evaluator = createEvaluator();
    Environment environment = createGlobalEnvironment(reader, evaluator);

    Repl repl = new Repl(reader, evaluator, environment, new DefaultPrinter());
    repl.run();
  }

  private Evaluator createEvaluator() {
    Evaluator evaluator = new Evaluator();

    AtomEvaluator atomEvaluator = createAtomEvaluator(evaluator);
    ListEvaluator listEvaluator = createListEvaluator(evaluator);

    evaluator.setAtomEvaluator(atomEvaluator);
    evaluator.setListEvaluator(listEvaluator);

    return evaluator;
  }

  private AtomEvaluator createAtomEvaluator(Evaluator evaluator) {
    AtomEvaluator atomEvaluator = new AtomEvaluator();
    atomEvaluator.setExpressionEvaluator(evaluator);

    return atomEvaluator;
  }

  private ListEvaluator createListEvaluator(Evaluator evaluator) {
    ListEvaluator listEvaluator = new ListEvaluator();
    listEvaluator.setExpressionEvaluator(evaluator);

    return listEvaluator;
  }

  private Environment createGlobalEnvironment(Reader reader, ExpressionEvaluator<Expression> evaluator) {
    Environment environment = new DefaultEnvironment();

    try {
      initBuiltins(environment);

      InputStream standardLibraryFileStream = Flij.class.getResourceAsStream(STANDARD_LIBRARY_PATH);
      evaluateFile(standardLibraryFileStream, reader, evaluator, environment);
    } catch (InitializationError e) {
      System.out.println(e.getMessage());
      System.exit(-1);
    }

    return environment;
  }

  private void initBuiltins(Environment environment) {
    new FastClasspathScanner(BUILTINS_PACKAGE_NAME).scan()
        .getNamesOfClassesWithAnnotationsAnyOf(RegisteredBuiltin.class, RegisteredVariantBuiltin.class)
        .forEach(classname -> {
          try {
            Class<?> clazz = Class.forName(classname);

            RegisteredBuiltin nonvariantAnnotation = clazz.getAnnotation(RegisteredBuiltin.class);
            if (nonvariantAnnotation != null) {
              String symbol = nonvariantAnnotation.value();

              Constructor<?> constructor = clazz.getConstructor(String.class);
              Builtin instance = (Builtin) constructor.newInstance(symbol);

              environment.setDefinition(symbol, instance);
            } else {
              RegisteredVariantBuiltin variantAnnotation = clazz.getAnnotation(RegisteredVariantBuiltin.class);

              if (variantAnnotation != null) {
                for (SymbolMapping mapping : variantAnnotation.value()) {
                  Constructor<?> constructor = clazz.getConstructor(String.class, String.class);
                  Builtin instance = (Builtin) constructor.newInstance(mapping.symbol(), mapping.variant());

                  environment.setDefinition(mapping.symbol(), instance);
                }
              }
            }
          } catch (Exception e) {
            throw new InitializationError("Could not initialize builtin " + classname);
          }
        });
  }

  private void evaluateFile(InputStream fileStream, Reader reader, ExpressionEvaluator<Expression> evaluator,
      Environment environment) {
    try {
      Scanner scanner = new Scanner(fileStream);
      while (scanner.hasNextLine()) {
        evaluator.evaluate(reader.read(scanner.nextLine(), scanner), environment);
      }
    } catch (Exception e) {
      throw new InitializationError("Could not load standard library");
    }
  }

}
