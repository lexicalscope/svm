package com.lexicalscope.symb.vm.symbinstructions.symbols;


public class ISymbol implements Symbol {
	private final int name;

	public ISymbol(final int name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("#%d", name);
	}

	@Override
	public int hashCode() {
		return name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			return name == ((ISymbol) obj).name;
		}
		return false;
	}

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.intSymbol(name);
   }
}
