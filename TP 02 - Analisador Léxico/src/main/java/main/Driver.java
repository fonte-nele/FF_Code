package main;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import error.CompilerError;
import java_cup.runtime.ComplexSymbolFactory;
import parse.Lexer;
import parse.Terminals;

// command line options
class DriverOptions {
   @Parameter(description = "<source file>")
   public List<String> parameters = new ArrayList<>();

   @Parameter(names = {"--help", "-h"}, description = "Usage help", help = true)
   public boolean help = false;

   @Parameter(names = {"--lexer", "-l"}, description = "Lexical analysis")
   public boolean lexer = false;
}

// main
public class Driver {

   public static void main(String[] args) {
      // parse command line options
      final DriverOptions options = new DriverOptions();
      final JCommander jCommander = new JCommander(options);
      jCommander.setProgramName("Driver");

      try {
         jCommander.parse(args);
      }
      catch (ParameterException e) {
         System.out.println(e.getMessage());
         jCommander.usage();
         System.exit(1);
      }

      if (options.help) {
         jCommander.usage();
         return;
      }

      Reader input = null;
      final String name;
      try {
         // set the input (source code) to compile
         if (options.parameters.isEmpty()) {
            System.out.println("Sem parametros");
            name = "unknown";
            input = new InputStreamReader(System.in);
         }
         else {
            System.out.println("Lendo arquivo");
            name = options.parameters.get(0);
            input = new FileReader(name);
         }

         // do only lexical analyses
         if (options.lexer) {
            System.out.println("Aquii");
            lexicalAnalysis(name, input);
         }
      }
      catch (CompilerError e) {
         System.out.println(e.getMessage());
         System.exit(3);
      }
      catch (IOException e) {
         System.out.println(e.getMessage());
         System.exit(2);
      }
      catch (Exception e) {
         System.out.println(e.getMessage());
         e.printStackTrace();
         System.exit(3);
      }
      finally {
         // closes the input file
         if (input instanceof FileReader)
            try {
               input.close();
            }
            catch (IOException e) {
               System.out.println(e.getMessage());
               System.exit(4);
            }
      }
   }

   private static void lexicalAnalysis(String name, Reader input) throws IOException {
      final Lexer lexer = new Lexer(input, name);
      ComplexSymbolFactory.ComplexSymbol tok;
      do {
         tok = (ComplexSymbolFactory.ComplexSymbol) lexer.next_token();
         System.out.println(Terminals.dumpTerminal(tok));
      } while (tok.sym != Terminals.EOF);
   }

}
