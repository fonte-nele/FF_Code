package absyn;

import env.Env;
import io.vavr.collection.Tree;
import parse.Loc;
import types.BOOL;
import types.Type;
import types.VOID;

import static error.ErrorHelper.typeMismatch;

public class ExpWhile extends Exp {
   public final Exp test;
   public final Exp body;

   public ExpWhile(Loc loc, Exp test, Exp body) {
      super(loc);
      this.test = test;
      this.body = body;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpWhile"),
                     test.toTree(),
                     body.toTree());
   }

   @Override
   protected Type semantic_(Env env) {
      // COMPLETE THIS CODE AND FIX THE RETURNED VALUE
      return VOID.T;
   }
}
