package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentation;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.State;

public class TraceMethodCalls implements Instrumentation {
   private final Matcher<? super State> matcher;

   public TraceMethodCalls(final Matcher<? super State> matcher) {
      this.matcher = matcher;
   }

   public static TraceMethodCalls methodCallsAndReturnsThatCross(final Matcher<? super State> matcher) {
      return new TraceMethodCalls(matcher);
   }

   @Override public Instruction instrument(final InstructionSource instructions, final Instruction methodEntry) {
      return statements(instructions).
            before(methodEntry).
            linearOp(new TraceMethodCallOp(matcher)).
            buildInstruction();
   }
}
