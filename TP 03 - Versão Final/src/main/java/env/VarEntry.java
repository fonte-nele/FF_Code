package env;

import types.Type;

public class VarEntry extends Entry {
   public final Type type;

   public VarEntry(Type type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return "VarEntry{" +
         "type=" + type +
         '}';
   }
}
