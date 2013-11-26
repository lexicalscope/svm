package com.lexicalscope.symb.vm.symbinstructions.symbols;


public class ValueSymbol implements Symbol {
	private final int name;

	public ValueSymbol(int name) {
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
	public boolean equals(Object obj) {
		if (obj != null && obj.getClass().equals(this.getClass())) {
			return name == ((ValueSymbol) obj).name;
		}
		return false;
	}
}
