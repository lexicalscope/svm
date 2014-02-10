package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.InstructionCode;
import com.lexicalscope.svm.vm.j.Vop;

public abstract class AbstractInstructionSink implements InstructionSource.InstructionSink {
   private final InstructionSource source;

   public AbstractInstructionSink(final InstructionSource source) {
      this.source = source;
   }

   @Override public final void nextOp(final Vop op, final InstructionCode code) {
      assert !(op instanceof Instruction);
      nextInstruction(new InstructionInternal(op, code));
   }

   @Override public final void linearOp(final Vop node, final InstructionCode code) {
      assert !(node instanceof LinearInstruction);
      nextOp(new LinearInstruction(node), code);
   }

   @Override public void noOp() { }

   protected abstract void nextInstruction(InstructionInternal node);
}
