package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class IArraySelectSymbol implements ISymbol {
   private final IArraySymbol arraySymbol;
   private final ISymbol indexSymbol;

   public IArraySelectSymbol(final IArraySymbol arraySymbol, final ISymbol indexSymbol) {
      this.arraySymbol = arraySymbol;
      this.indexSymbol = indexSymbol;
   }

   @Override public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.iarraySelect(arraySymbol, indexSymbol);
   }

   @Override
   public String toString() {
      return String.format("(select %s %s)", arraySymbol, indexSymbol);
   }

   @Override
   public int hashCode() {
      return arraySymbol.hashCode() ^ indexSymbol.hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if(this == obj) {
         return true;
      }
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final IArraySelectSymbol that = (IArraySelectSymbol) obj;
         return this.arraySymbol.equals(that.arraySymbol) &&
               this.indexSymbol.equals(that.indexSymbol);
      }
      return false;
   }
}
