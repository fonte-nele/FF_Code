package absyn;

import env.Entry;
import env.Env;
import env.FunEntry;
import io.vavr.collection.List;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;

import static error.ErrorHelper.*;

public class ExpCall extends Exp {
   public final String function;
   public final List<Exp> arguments;

   public ExpCall(Loc loc, String function, List<Exp> arguments) {
      super(loc);
      this.function = function;
      this.arguments = arguments;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(annotateType("ExpCall: " + function),
                     arguments.map(Exp::toTree));
   }

   @Override
   protected Type semantic_(Env env) {
      Entry entry = env.venv.get(function);
      if (entry == null)
         throw undefined(loc, "function", function);
      if (!(entry instanceof FunEntry))
         throw notAFunction(loc, function);
      FunEntry fentry = (FunEntry) entry;
      if (arguments.size() < fentry.formals.size())
         throw tooFewArguments(loc, function);
      if (arguments.size() > fentry.formals.size())
         throw tooMuchArguments(loc, function);
      fentry.formals.zipWith(arguments,
                             (f, a) -> {
                                if (!a.semantic(env).is(f))
                                   throw typeMismatch(a.loc, a.type, f);
                                return 0;
                             });
      return fentry.result;
   }
}
