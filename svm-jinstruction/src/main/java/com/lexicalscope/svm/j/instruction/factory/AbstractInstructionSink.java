package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.factory.Instructions.InstructionSink;
import com.lexicalscope.symb.vm.j.Vop;

public abstract class AbstractInstructionSink implements InstructionSink {
   @Override public final void linearInstruction(final Vop node) {
      assert !(node instanceof LinearInstruction);
      nextInstruction(new LinearInstruction(node));
   }
   @Override public void noInstruction() { }
}
