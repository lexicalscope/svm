package com.lexicalscope.svm.j.instruction.symbolic.symbols;



public class INegSymbol implements ISymbol {
	private final ISymbol value;

	public INegSymbol(final ISymbol value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			final INegSymbol that = (INegSymbol) obj;
			return that.value.equals(this.value);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("(- %s)", value);
	}

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.neg(value);
   }
}
