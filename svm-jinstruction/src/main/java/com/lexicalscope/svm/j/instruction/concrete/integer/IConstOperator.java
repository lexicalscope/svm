package com.lexicalscope.svm.j.instruction.concrete.integer;

import com.lexicalscope.svm.j.instruction.concrete.NullaryOperator;


public class IConstOperator implements NullaryOperator {
	private final int val;

   public IConstOperator(final int val) {
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
