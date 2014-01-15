package com.lexicalscope.symb.vm.symbinstructions.symbols;



public class MulSymbol implements ISymbol {
	private final ISymbol left;
	private final ISymbol right;

	public MulSymbol(final ISymbol left, final ISymbol right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int hashCode() {
		return left.hashCode() ^ right.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			final MulSymbol that = (MulSymbol) obj;
			return that.left.equals(this.left) && that.right.equals(this.right);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("(* %s %s)", left, right);
	}

   @Override
   public <T, E extends Throwable> T accept(final SymbolVisitor<T, E> visitor) throws E {
      return visitor.mul(left, right);
   }
}
