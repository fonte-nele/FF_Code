package absyn;

import env.Entry;
import env.Env;
import env.VarEntry;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;
import types.VOID;

import static error.ErrorHelper.notAVariable;
import static error.ErrorHelper.typeMismatch;
import static error.ErrorHelper.undefined;

public class ExpAssign extends Exp {
   public final String variable;
   public final Exp expression;

   public ExpAssign(Loc loc, String variable, Exp expression) {
      super(loc);
      this.variable = variable;
      this.expression = expression;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpAssign"),
                     Tree.of(variable),
                     expression.toTree());
   }

   @Override
   protected Type semantic_(Env env) {
      // COMPLETE THIS CODE AND FIX THE RETURNED VALUE
      return VOID.T;
   }
}
