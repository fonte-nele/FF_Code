package absyn;

import env.Entry;
import env.Env;
import env.FunEntry;
import io.vavr.collection.List;
import io.vavr.collection.Tree;
import io.vavr.control.Option;
import parse.Loc;
import types.INT;

import static error.ErrorHelper.*;

public class Program extends AST {
   public final List<Fun> functions;

   public Program(Loc loc, List<Fun> functions) {
      super(loc);
      this.functions = functions;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of("Program", functions.map(Fun::toTree));
   }

   // A program is a sequence of one or more mutually recursive
   // function declarations. There should be at least one function
   // named main returning an integer and with an integer argument.

   // Semantic analysis of the program should check each function
   // declaration, adding it to the environemnt, as well as check the
   // main function existence and signature.

   // Do semantic analysis of the function
   public void semantic(Env env) {
      // COMPLETE THIS CODE
   }

}
