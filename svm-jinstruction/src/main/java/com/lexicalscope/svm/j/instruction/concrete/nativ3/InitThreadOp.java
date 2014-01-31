package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class InitThreadOp implements Vop {
   @Override public void eval(final State ctx) {
      final SClass threadClass = ctx.load(JavaConstants.THREAD_CLASS);
      final Object address = ctx.newObject(threadClass);
      ctx.put(address, SClass.OBJECT_MARKER_OFFSET, threadClass);
      ctx.currentThreadIs(address);
      ctx.push(address);
   }

   @Override public String toString() {
      return "INIT_THREAD";
   }

   public static Instruction initThreadInstruction(final InstructionSource instructions) {
      return instructions.statements()
         .linear(new InitThreadOp())
         .createInvokeSpecial(new AsmSMethodName("java/lang/Thread", "<init>", "()V"))
         .buildInstruction();
   }
}
