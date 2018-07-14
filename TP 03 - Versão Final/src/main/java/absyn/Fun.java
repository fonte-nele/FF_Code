package absyn;

import env.Entry;
import env.Env;
import env.FunEntry;
import env.VarEntry;
import io.vavr.collection.List;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;
import types.VOID;

import static error.ErrorHelper.error;
import static error.ErrorHelper.fatal;
import static error.ErrorHelper.typeMismatch;

public class Fun extends AST {
   public final TypeId name;
   public final List<TypeId> parameters;
   public final Exp body;

   public Fun(Loc loc, TypeId name, List<TypeId> parameters, Exp body) {
      super(loc);
      this.name = name;
      this.parameters = parameters;
      this.body = body;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of("Fun",
                     name.toTree(),
                     Tree.of("Parameters", parameters.map(TypeId::toTree))  ,
                     body.toTree());
   }

   // Semantic analysis of mutual recursive function declarations are
   // done in two phases.

   // First the signature of each function declarations (type of
   // formal parameters and type of result) is calculated and the
   // function name is added to the environemnt.

   // Then the body of each function is checked using such extended
   // environment.

   // Add the signature of the function to the environment
   public void addSignature(Env env) {
      // COMPLETE THIS CODE
   }

   // Check the function body
   public void checkBody(Env env) {
      // COMPLETE THIS CODE
   }

}
