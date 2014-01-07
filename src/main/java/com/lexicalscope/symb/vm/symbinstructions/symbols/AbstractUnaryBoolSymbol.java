package com.lexicalscope.symb.vm.symbinstructions.symbols;

public abstract class AbstractUnaryBoolSymbol implements BoolSymbol {
   protected final ISymbol val;

   public AbstractUnaryBoolSymbol(final ISymbol val) {
      this.val = val;
   }

   @Override
   public int hashCode() {
      return val.hashCode() ^ getClass().hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final AbstractUnaryBoolSymbol that = (AbstractUnaryBoolSymbol) obj;
         return that.val.equals(this.val);
      }
      return false;
   }
}
