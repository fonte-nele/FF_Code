package absyn;

import env.Env;
import types.Type;

public enum TypeName {
   VOID {
      @Override
      public Type semantic(Env env) {
         return types.VOID.T;
      }
   },

   INT {
      @Override
      public Type semantic(Env env) {
         return types.INT.T;
      }
   };

   // COMPLETE THIS CODE FOR THE MISSING TYPES

   public abstract Type semantic(Env env);
}
