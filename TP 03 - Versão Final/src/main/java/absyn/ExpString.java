package absyn;

import env.Env;
import io.vavr.collection.Tree;
import parse.Loc;
import types.STRING;
import types.Type;
import types.VOID;

public class ExpString extends Exp {
   public final String value;

   public ExpString(Loc loc, String value) {
      super(loc);
      this.value = value;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpString: " + value));
   }

   @Override
   protected Type semantic_(Env env) {
      // COMPLETE THIS CODE AND FIX THE RETURNED VALUE
      return VOID.T;
   }

}
