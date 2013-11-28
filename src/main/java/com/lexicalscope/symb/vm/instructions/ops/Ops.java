package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.stackFrameOps.PopOperand;

public class Ops {
	public static LoadConstants loadConstants(final Object ... values) {
		return new LoadConstants(values);
	}

	public static PopOperand popOperand() {
      return new PopOperand();
   }
}
