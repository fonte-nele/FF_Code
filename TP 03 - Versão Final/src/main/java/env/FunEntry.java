package env;

import io.vavr.collection.List;
import types.Type;

public class FunEntry extends Entry {
   public final List<Type> formals;
   public final Type result;

   public FunEntry(List<Type> formals, Type result) {
      this.formals = formals;
      this.result = result;
   }

   @Override
   public String toString() {
      return "FunEntry{" +
         "formals=" + formals +
         ", result=" + result +
         '}';
   }
}
