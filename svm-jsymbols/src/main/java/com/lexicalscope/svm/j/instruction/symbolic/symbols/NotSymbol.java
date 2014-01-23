package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class NotSymbol implements BoolSymbol {
   private final BoolSymbol val;

   public NotSymbol(final BoolSymbol val) {
      this.val = val;
   }

   @Override
   public int hashCode() {
      return val.hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final NotSymbol that = (NotSymbol) obj;
         return that.val.equals(this.val);
      }
      return false;
   }

   @Override
   public String toString() {
      return String.format("(! %s)", val);
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.not(val);
   }
}
