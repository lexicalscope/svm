package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.nextInstruction;

import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class LinearInstruction implements Instruction {
   private final Vop op;

   public LinearInstruction(final Vop op) {
      this.op = op;
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      state.op(nextInstruction(instruction));
      state.op(op);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
