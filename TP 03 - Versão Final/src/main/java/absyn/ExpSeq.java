package absyn;

import env.Env;
import io.vavr.collection.List;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;
import types.VOID;

public class ExpSeq extends Exp {
   public final List<Exp> sequence;

   public ExpSeq(Loc loc, List<Exp> sequence) {
      super(loc);
      this.sequence = sequence;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpSeq"), sequence.map(Exp::toTree));
   }

   @Override
   protected Type semantic_(Env env) {
      sequence.forEach(exp -> exp.semantic(env));
      return sequence.isEmpty() ? VOID.T : sequence.last().type;
   }
}
