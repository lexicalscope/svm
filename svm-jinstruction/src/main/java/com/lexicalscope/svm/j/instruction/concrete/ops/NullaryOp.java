package com.lexicalscope.svm.j.instruction.concrete.ops;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

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

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.nularyop();
   }
}
