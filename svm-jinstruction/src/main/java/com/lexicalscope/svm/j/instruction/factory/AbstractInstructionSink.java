package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.Op;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

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

   @Override public final void loadingOp(final List<String> classes, final Vop op) {
      nextOp(new LoadingInstruction(classes, op, source));
   }

   @Override public final void loadingOp(final Op<List<SClass>> loader, final Vop op) {
      nextOp(new LoadingInstruction(loader, op, source));
   }

   @Override public void noOp() { }


   protected abstract void nextInstruction(Instruction node);
}
