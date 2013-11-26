package com.lexicalscope.symb.vm.symbinstructions.symbols;



public class AddSymbol implements Symbol {
	private final Symbol left;
	private final Symbol right;

	public AddSymbol(Symbol left, Symbol right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int hashCode() {
		return left.hashCode() ^ right.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			AddSymbol that = (AddSymbol) obj;
			return that.left.equals(this.left) && that.right.equals(this.right);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("(+ %s %s)", left, right);
	}
}
