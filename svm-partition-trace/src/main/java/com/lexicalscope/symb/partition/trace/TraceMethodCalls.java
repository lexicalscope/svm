package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.State;

public class TraceMethodCalls implements Instrumentor {
   private final Matcher<? super State> matcher;

   public TraceMethodCalls(final Matcher<? super State> matcher) {
      this.matcher = matcher;
   }

   public static TraceMethodCalls methodCallsAndReturnsThatCross(final Matcher<? super State> matcher) {
      return new TraceMethodCalls(matcher);
   }

   @Override public Instruction instrument(final InstructionSource instructions, final Instruction methodEntry) {
      return instrumentMethodReturn(instructions, instrumentMethodCall(instructions, methodEntry));
   }

   private Instruction instrumentMethodReturn(final InstructionSource instructions, final Instruction methodEntry) {
      Instruction cur = methodEntry;
      while(!cur.code().isMethodExit()) {
         if(cur.code().isReturn()) {
            // TODO
         }
         cur = cur.next();
      }
      return methodEntry;
   }

   private Instruction instrumentMethodCall(final InstructionSource instructions, final Instruction methodEntry) {
      return statements(instructions).
            before(methodEntry).
            linearOp(new TraceMethodCallOp(matcher)).
            buildInstruction();
   }
}
