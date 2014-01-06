package com.lexicalscope.symb.vm.symbinstructions.symbols;

public abstract class AbstractUnarySymbol implements ISymbol {
   protected final ISymbol val;

   public AbstractUnarySymbol(final ISymbol val) {
      this.val = val;
   }

   @Override
   public int hashCode() {
      return val.hashCode() ^ getClass().hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final AbstractUnarySymbol that = (AbstractUnarySymbol) obj;
         return that.val.equals(this.val);
      }
      return false;
   }
}
