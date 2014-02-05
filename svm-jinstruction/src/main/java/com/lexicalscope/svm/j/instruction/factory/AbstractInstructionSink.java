package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.Vop;

public abstract class AbstractInstructionSink implements InstructionSource.InstructionSink {
   private final InstructionSource source;

   public AbstractInstructionSink(final InstructionSource source) {
      this.source = source;
   }

   @Override public final void nextOp(final Vop op) {
      assert !(op instanceof Instruction);
      nextInstruction(new InstructionInternal(op));
   }

   @Override public final void linearOp(final Vop node) {
      assert !(node instanceof LinearInstruction);
      nextOp(new LinearInstruction(node));
   }

   @Override public void noOp() { }


   protected abstract void nextInstruction(InstructionInternal node);
}
