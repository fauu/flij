package com.github.fauu.flij;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import com.github.fauu.flij.builtin.AddBuiltin;
import com.github.fauu.flij.builtin.CondBuiltin;
import com.github.fauu.flij.builtin.DefineBuiltin;
import com.github.fauu.flij.builtin.DivideBuiltin;
import com.github.fauu.flij.builtin.EqualsBuiltin;
import com.github.fauu.flij.builtin.LambdaBuiltin;
import com.github.fauu.flij.builtin.LengthBuiltin;
import com.github.fauu.flij.builtin.MultiplyBuiltin;
import com.github.fauu.flij.builtin.SubBuiltin;
import com.github.fauu.flij.evaluator.AtomEvaluator;
import com.github.fauu.flij.evaluator.Environment;
import com.github.fauu.flij.evaluator.Evaluator;
import com.github.fauu.flij.evaluator.ExpressionEvaluator;
import com.github.fauu.flij.evaluator.ListEvaluator;
import com.github.fauu.flij.expression.Expression;
import com.github.fauu.flij.reader.Reader;
import com.github.fauu.flij.repl.Repl;

public class Flij {
  
  private static String STANDARD_LIBRARY_PATH = "std.flj";

  public void run() {
    Reader reader = new Reader();
    Evaluator evaluator = createEvaluator();
    Environment environment = createGlobalEnvironment(reader, evaluator);

    Repl repl = new Repl(reader, evaluator, environment);
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
    Arrays.asList(
        new DefineBuiltin("def"),
        new LambdaBuiltin("fn"),
        new EqualsBuiltin("="),
        new CondBuiltin("cond"),
        new AddBuiltin("+"),
        new SubBuiltin("-"),
        new MultiplyBuiltin("*"),
        new DivideBuiltin("/"),
        new LengthBuiltin("len")
    ).forEach(b -> environment.setDefinition(b.getSymbol(), b));
  }
  
  private void loadStandardLibrary(Reader reader, ExpressionEvaluator<Expression> evaluator, Environment environment) {
    try {
      String fullPath = getClass()
          .getClassLoader()
          .getResource(STANDARD_LIBRARY_PATH)
          .toString();
      
      // XXX: Hack to support loading when launching from both Equinox Launcher and JAR
      String[] fragments = fullPath.toString().split("!"); // <jar path>!<path inside jar>
      String path = fragments.length > 1 ? // Are we running from JAR?
          "src/main/resources/lib" + fragments[1] : // <path inside jar>
          fragments[0].substring(5, fragments[0].length()); // Strip "file:"

      Files.lines(Paths.get(path))
           .map(l -> l.trim())
           .filter(l -> !l.isEmpty())
           .forEach(l -> evaluator.evaluate(reader.read(l), environment));
    } catch (IOException e) {
      System.out.println("Could not load standard library file");
    }
  }

}
