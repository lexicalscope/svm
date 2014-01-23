package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public final class IConstSymbol implements ISymbol {
	private final int val;

   public IConstSymbol(final int val) {
      this.val = val;
	}

   public int val() {
      return val;
   }

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.constant(val);
   }

   @Override
   public int hashCode() {
      return val;
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final IConstSymbol that = (IConstSymbol) obj;
         return that.val == this.val;
      }
      return false;
   }

   @Override
   public String toString() {
      return "" + val;
   }
}
