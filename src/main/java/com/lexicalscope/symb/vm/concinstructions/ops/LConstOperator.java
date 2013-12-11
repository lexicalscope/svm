package com.lexicalscope.symb.vm.concinstructions.ops;

import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;

public class LConstOperator implements NullaryOperator {
	private final long val;

   public LConstOperator(final long val) {
      this.val = val;
   }

   @Override
	public Object eval() {
		return val;
	}

	@Override
	public String toString() {
		return "ICONST_" + val;
	}
}
