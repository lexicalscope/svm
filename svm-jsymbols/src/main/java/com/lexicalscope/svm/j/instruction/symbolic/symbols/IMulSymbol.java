package com.lexicalscope.svm.j.instruction.symbolic.symbols;



public class IMulSymbol implements ISymbol {
	private final ISymbol left;
	private final ISymbol right;

	public IMulSymbol(final ISymbol left, final ISymbol right) {
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
			final IMulSymbol that = (IMulSymbol) obj;
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
