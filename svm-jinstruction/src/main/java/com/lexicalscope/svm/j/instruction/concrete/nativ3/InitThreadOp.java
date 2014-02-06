package com.lexicalscope.svm.j.instruction.concrete.nativ3;

import static com.lexicalscope.symb.vm.j.j.code.AsmSMethodName.defaultConstructor;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
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

   public static void initThreadInstruction(final StatementBuilder statements) {
      statements
         .linearOp(new InitThreadOp())
         .createInvokeSpecial(defaultConstructor("java/lang/Thread"));
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.synthetic();
   }
}
