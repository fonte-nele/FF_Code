package absyn;

import env.Env;
import io.vavr.collection.Tree;
import parse.Loc;
import types.BOOL;
import types.Type;
import types.VOID;

import static error.ErrorHelper.typeMismatch;

public class ExpCond extends Exp {
   public final Exp test;
   public final Exp alt1;
   public final Exp alt2;

   public ExpCond(Loc loc, Exp test, Exp alt1, Exp alt2) {
      super(loc);
      this.test = test;
      this.alt1 = alt1;
      this.alt2 = alt2;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpCond"),
                     test.toTree(),
                     alt1.toTree(),
                     alt2.toTree());
   }

   @Override
   protected Type semantic_(Env env) {
      // COMPLETE THIS CODE AND FIX THE RETURNED VALUE
      return VOID.T;
   }
}
