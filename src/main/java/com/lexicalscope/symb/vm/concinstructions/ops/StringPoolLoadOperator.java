package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;

public class StringPoolLoadOperator implements NullaryOperator {
	private final String val;

   public StringPoolLoadOperator(final String val) {
      this.val = val;
   }

   @Override
	public Object eval() {
		return val;
	}

	@Override
	public String toString() {
		return "LDC " + val;
	}
}
