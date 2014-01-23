package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class IArrayStoreSymbol implements IArraySymbol {
   private final IArraySymbol arraySymbol;
   private final ISymbol indexSymbol;
   private final ISymbol valueSymbol;

   public IArrayStoreSymbol(final IArraySymbol arraySymbol, final ISymbol indexSymbol, final ISymbol valueSymbol) {
      this.arraySymbol = arraySymbol;
      this.indexSymbol = indexSymbol;
      this.valueSymbol = valueSymbol;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.iarrayStore(arraySymbol, indexSymbol, valueSymbol);
   }

   @Override
   public String toString() {
      return String.format("(store %s %s %s)", arraySymbol, indexSymbol, valueSymbol);
   }

   @Override
   public int hashCode() {
      return arraySymbol.hashCode() ^ indexSymbol.hashCode() ^ valueSymbol.hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if(this == obj) {
         return true;
      }
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final IArrayStoreSymbol that = (IArrayStoreSymbol) obj;
         return this.arraySymbol.equals(that.arraySymbol) &&
               this.indexSymbol.equals(that.indexSymbol) &&
               this.valueSymbol.equals(that.valueSymbol);
      }
      return false;
   }
}
