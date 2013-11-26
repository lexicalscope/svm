package com.lexicalscope.symb.vm.symbinstructions.symbols;

public class ConstSymbol implements Symbol {
	private final Object val;

   public ConstSymbol(final Object val) {
      this.val = val;
	}

   @Override
   public int hashCode() {
      return val.hashCode();
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final ConstSymbol that = (ConstSymbol) obj;
         return that.val.equals(this.val);
      }
      return false;
   }

   @Override
   public String toString() {
      return val.toString();
   }
}
