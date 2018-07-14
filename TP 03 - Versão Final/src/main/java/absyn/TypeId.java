package absyn;

import env.Env;
import io.vavr.collection.Tree;
import parse.Loc;
import types.Type;

public class TypeId extends AST {
   public final TypeName type;
   public final String id;

   public TypeId(Loc loc, TypeName type, String id) {
      super(loc);
      this.type = type;
      this.id = id;
   }

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of("TypeId",
                     Tree.of(type.toString()),
                     Tree.of(id));
   }

   Type semantic(Env env) {
      return type.semantic(env);
   }
}
