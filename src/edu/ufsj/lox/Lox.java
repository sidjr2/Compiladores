package edu.ufsj.lox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;

<<<<<<< Updated upstream
public class Lox {
=======
abstract class Expr{
  static class Binary extends Expr{
    Binary(Expr left, Token operator, Expr right){
      this.left = left;
      this.operator = operator;
      this.right = right;
    }
    final Expr left;
    final Token operator;
    final Expr right;
  }

  public static class Lox {
>>>>>>> Stashed changes
	static boolean hadError = false;
	public static void main(final String args[]) throws IOException {
        if (args.length > 1) {
            System.err.println("Usage: jlox [script");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(final String path) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError)
            System.exit(64);
    }
    
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
    
        for (;;) { 
          System.out.print("> ");
          String line = reader.readLine();
          if (line == null) break;
          run(line);
          hadError = false;
        }
      }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        Expr expression = parser.parse();

        // Stop if there was a syntax error.
        if (hadError) return;

        System.out.println(new AstPrinter().print(expression));
      }

    static void error(int line, String message) {
        report(line, "", message);
      }
    
    private static void report(int line, String where,String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    static void error(Token token, String message) {
      if (token.type == TokenType.EOF) {
        report(token.line, " at end", message);
      } else {
        report(token.line, " at '" + token.lexeme + "'", message);
      }
    }
}

  public interface Visitor<T> {
  }

public String accept(AstPrinter astPrinter) {
    return null;
}

}