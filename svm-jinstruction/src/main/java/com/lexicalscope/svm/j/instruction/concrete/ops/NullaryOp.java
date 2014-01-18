package com.lexicalscope.svm.j.instruction.concrete.ops;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class NullaryOp implements Vop {
	private final NullaryOperator operator;

	public NullaryOp(final NullaryOperator operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return operator.toString();
	}

   @Override public void eval(final State ctx) {
      ctx.push(operator.eval());
   }
}
