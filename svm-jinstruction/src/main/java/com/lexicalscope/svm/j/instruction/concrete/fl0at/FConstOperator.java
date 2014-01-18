package com.lexicalscope.svm.j.instruction.concrete.fl0at;

import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;


public class FConstOperator implements NullaryOperator {
	private final float val;

   public FConstOperator(final float val) {
      this.val = val;
   }

   @Override
	public Object eval() {
		return val;
	}

	@Override
	public String toString() {
		return "FCONST_" + val;
	}
}
