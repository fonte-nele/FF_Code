package types;

import io.vavr.collection.List;
import io.vavr.collection.Tree;
import io.vavr.render.ToTree;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public abstract class Type implements ToTree<String> {

   @Override
   public Tree.Node<String> toTree() {
      return Tree.of(toString());
   }

   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

   public Type actual() {
      return this;
   }

   // Verify if this type can be coerced to the given type. In
   // general one type can be coerced to another type if and only if the
   // types are the same.
   public boolean is(Type type) {
      return type.actual() == this;
   }

   // Verify if this type can be coerced to any of the given types.
   public boolean is(Type... types) {
      return List.of(types).exists(t -> this.is(t));
   }
}
