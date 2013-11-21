package com.lexicalscope.symb.vm.instructions.transformers;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.instructions.StateTransformer;
import com.lexicalscope.symb.vm.instructions.ops.OperandsOp;

public class OperandsTransformer implements StateTransformer {
   private final OperandsOp op;

   public OperandsTransformer(final OperandsOp op) {
      this.op = op;
   }

   @Override
   public State transform(final State state, final Instruction nextInstruction) {
      return state.op(nextInstruction, op);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
