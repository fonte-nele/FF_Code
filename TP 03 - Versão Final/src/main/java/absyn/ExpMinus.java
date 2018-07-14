package absyn;

import env.Env;
import io.vavr.collection.Tree;
import parse.Loc;
import types.INT;
import types.Type;
import types.VOID;

import static error.ErrorHelper.typeMismatch;

public class ExpMinus extends Exp {
   public final Exp operand;

   public ExpMinus(Loc loc, Exp operand) {
      super(loc);
      this.operand = operand;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpMinus"),
                     operand.toTree());
   }

   @Override
   protected Type semantic_(Env env) {
      // COMPLETE THIS CODE AND FIX THE RETURNED VALUE
      return VOID.T;
   }
}
