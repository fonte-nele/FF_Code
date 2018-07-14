package types;

public class VOID extends Type {

   public static final VOID T = new VOID();

   private VOID() {
   }

   @Override
   public String toString() {
      return "void";
   }
}
