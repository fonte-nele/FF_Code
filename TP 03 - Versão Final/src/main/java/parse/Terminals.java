package parse;

import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

public interface Terminals extends SymbolConstants {

   static String dumpTerminal(Symbol tok) {
      if (tok instanceof ComplexSymbol) {
         ComplexSymbol t = (ComplexSymbol) tok;
         final Loc loc = Loc.loc(t.getLeft(), t.getRight());
         return String.format("%s %s", loc, dumpSimpleTerminal(t));
      }
      else
         return dumpSimpleTerminal(tok);
   }

   static String dumpSimpleTerminal(Symbol tok) {
      if (tok.value == null)
         return terminalNames[tok.sym];
      else
         return String.format("%s(%s)", terminalNames[tok.sym], tok.value);
   }

}
