package com.lexicalscope.symb.vm.symbinstructions.symbols;

public class GeSymbol implements ISymbol {
   private final ISymbol val;

   public GeSymbol(final ISymbol val) {
      this.val = val;
   }

   @Override
   public int hashCode() {
      return val.hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final GeSymbol that = (GeSymbol) obj;
         return that.val.equals(this.val);
      }
      return false;
   }

   @Override
   public String toString() {
      return String.format("(>= %s 0)", val);
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.ge(val);
   }
}
