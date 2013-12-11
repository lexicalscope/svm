package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.nextInstruction;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.instructions.ops.DefineClassOp;

public class LoadingInstruction implements Instruction {
   private final String klassDesc;
   private final Vop op;

   public LoadingInstruction(final String klassDesc, final Vop op) {
      this.klassDesc = klassDesc;
      this.op = op;
   }

   @Override public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      if(!state.op(new DefineClassOp(klassDesc))){
         state.op(nextInstruction(instruction));
         state.op(op);
      }
   }

   @Override public String toString() {
      return op.toString();
   }
}
