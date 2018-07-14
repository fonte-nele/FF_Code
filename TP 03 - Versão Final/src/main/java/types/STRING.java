package types;

public class STRING extends Type {

   public static final STRING T = new STRING();

   private STRING() {
   }

   @Override
   public String toString() {
      return "string";
   }
}
