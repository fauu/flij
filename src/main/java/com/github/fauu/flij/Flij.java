package com.github.fauu.flij;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.fauu.flij.builtin.Builtin;
import com.github.fauu.flij.builtin.SymbolMapping;
import com.github.fauu.flij.builtin.RegisteredBuiltin;
import com.github.fauu.flij.builtin.RegisteredVariantBuiltin;
import com.github.fauu.flij.evaluator.AtomEvaluator;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.Evaluator;
import com.github.fauu.flij.evaluator.ExpressionEvaluationException;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.ListEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.reader.Reader;
import com.github.fauu.flij.repl.Repl;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class Flij {

  private final static String STANDARD_LIBRARY_PATH = "std.flj";
  private final static String BUILTINS_PACKAGE = "com.github.fauu.flij.builtin";

  public void run() {
    Reader reader = new Reader();
    Evaluator evaluator = createEvaluator();
    Environment environment = createGlobalEnvironment(reader, evaluator);

    Repl repl = new Repl(reader, evaluator, environment, new Printer());
    repl.run();
  }

  private Evaluator createEvaluator() {
    Evaluator evaluator = new Evaluator();

    AtomEvaluator atomEvaluator = new AtomEvaluator();
    atomEvaluator.setExpressionEvaluator(evaluator);

    ListEvaluator listEvaluator = new ListEvaluator();
    listEvaluator.setExpressionEvaluator(evaluator);

    evaluator.setAtomEvaluator(atomEvaluator);
    evaluator.setListEvaluator(listEvaluator);

    return evaluator;
  }

  private Environment createGlobalEnvironment(Reader reader, ExpressionEvaluator<Expression> evaluator) {
    Environment environment = new Environment();

    initBuiltins(environment);
    loadStandardLibrary(reader, evaluator, environment);

    return environment;
  }

  private void initBuiltins(Environment environment) {
    new FastClasspathScanner(BUILTINS_PACKAGE)
        .scan()
        .getNamesOfClassesWithAnnotationsAnyOf(RegisteredBuiltin.class, RegisteredVariantBuiltin.class)
        .stream()
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

  private void loadStandardLibrary(Reader reader, ExpressionEvaluator<Expression> evaluator, Environment environment) {
    try {
      String fullPath = getClass().getClassLoader().getResource(STANDARD_LIBRARY_PATH).toString();

      // XXX: Hack to support loading when launching from both Equinox Launcher and
      // JAR
      String[] fragments = fullPath.toString().split("!"); // <jar path>!<path inside jar>
      String path = fragments.length > 1 ? // Are we running from JAR?
          "src/main/resources/lib" + fragments[1] : // <path inside jar>
          fragments[0].substring(5, fragments[0].length()); // Strip "file:"

      Files.lines(Paths.get(path)).map(l -> l.trim()).filter(l -> !l.isEmpty())
          .forEach(l -> evaluator.evaluate(reader.read(l), environment));
    } catch (ExpressionEvaluationException e) {
      System.out.println("Encountered error while evaluating stdlib:");
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println("Could not load standard library file");
    }
  }

}
