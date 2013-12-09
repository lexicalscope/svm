package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.nextInstruction;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;

public class LinearInstruction implements Instruction {
   private final StateTransformer transformer;

   public LinearInstruction(final StateTransformer transformer) {
      this.transformer = transformer;
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      state.op(nextInstruction(instruction));
      transformer.transform(state);
   }

   @Override
   public String toString() {
      return transformer.toString();
   }
}
