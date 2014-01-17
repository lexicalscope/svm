package com.lexicalscope.svm.j.instruction.concrete;


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
