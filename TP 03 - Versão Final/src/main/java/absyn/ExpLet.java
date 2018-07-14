package absyn;

import env.Env;
import env.VarEntry;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;

public class ExpLet extends Exp {
   public final String variable;
   public final Exp init;
   public final Exp body;

   public ExpLet(Loc loc, String variable, Exp init, Exp body) {
      super(loc);
      this.variable = variable;
      this.init = init;
      this.body = body;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpLet"),
                     Tree.of(variable),
                     init.toTree(),
                     body.toTree());
   }

   @Override
   protected Type semantic_(Env env) {
      init.semantic(env);
      env.venv.beginScope();
      env.venv.put(variable, new VarEntry(init.type));
      body.semantic(env);
      env.venv.endScope();
      return body.type;
   }
}
