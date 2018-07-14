package absyn;

import env.Entry;
import env.Env;
import env.VarEntry;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;

import static error.ErrorHelper.notAVariable;
import static error.ErrorHelper.undefined;

public class ExpVar extends Exp {
   public final String name;

   public ExpVar(Loc loc, String name) {
      super(loc);
      this.name = name;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpVar: " + name));
   }

   @Override
   protected Type semantic_(Env env) {
      Entry t = env.venv.get(name);
      if (t == null)
         throw undefined(loc, "variable", name);
      if (! (t instanceof VarEntry) )
         throw notAVariable(loc, name);
      return ((VarEntry) t).type;
   }

}
