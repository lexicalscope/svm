package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.symb.vm.j.Vop;

public abstract class AbstractInstructionSink implements InstructionSource.InstructionSink {
   @Override public final void linearInstruction(final Vop node) {
      assert !(node instanceof LinearInstruction);
      nextInstruction(new LinearInstruction(node));
   }

   @Override public void loadingInstruction(final List<String> classes, final Vop op, final InstructionSource source) {
      nextInstruction(new LoadingInstruction(classes, op, source));
   }

   @Override public void noInstruction() { }
}
