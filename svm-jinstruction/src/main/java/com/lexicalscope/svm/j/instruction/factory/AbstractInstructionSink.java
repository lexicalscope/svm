package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.Vop;

public abstract class AbstractInstructionSink implements InstructionSource.InstructionSink {
   @Override public void nextOp(final Vop op) {
      assert !(op instanceof Instruction);
      nextInstruction(new InstructionInternal(op));
   }

   @Override public final void linearOp(final Vop node) {
      assert !(node instanceof LinearInstruction);
      nextOp(new LinearInstruction(node));
   }

   @Override public void loadingOp(final List<String> classes, final Vop op, final InstructionSource source) {
      nextOp(new LoadingInstruction(classes, op, source));
   }

   @Override public void noOp() { }
}
