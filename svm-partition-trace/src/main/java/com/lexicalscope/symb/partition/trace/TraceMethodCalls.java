package com.lexicalscope.symb.partition.trace;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;
import static com.lexicalscope.symb.partition.trace.Trace.CallReturn.*;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.State;

public class TraceMethodCalls implements Instrumentor {
   private final Matcher<? super State> callinMatcher;
   private final Matcher<? super InstrumentationContext> callbackMatcher;

   public TraceMethodCalls(
         final Matcher<? super State> callinMatcher,
         final Matcher<? super InstrumentationContext> callbackMatcher) {
      this.callinMatcher = callinMatcher;
      this.callbackMatcher = callbackMatcher;
   }

   public static Instrumentor methodCallsAndReturnsThatCross(final PartitionBuilder partition) {
      return methodCallsAndReturnsThatCross(
            partition.dynamicExactCallinMatcher(),
            partition.dynamicExactCallbackMatcher());
   }

   private static Instrumentor methodCallsAndReturnsThatCross(
         final Matcher<? super State> dynamicExactCallinMatcher,
         final Matcher<? super InstrumentationContext> dynamicExactCallbackMatcher) {
      return new TraceMethodCalls(dynamicExactCallinMatcher, dynamicExactCallbackMatcher);
   }

   @Override public Instruction instrument(final InstructionSource instructions, final Instruction methodEntry) {
      return instrumentCallBacks(instructions,
               instrumentMethodReturn(instructions,
                     instrumentMethodCall(instructions, methodEntry)));
   }

   private Instruction instrumentMethodCall(final InstructionSource instructions, final Instruction methodEntry) {
      return statements(instructions).
            before(methodEntry).
            linearOp(new TraceMethodCallOp(callinMatcher, CALL)).
            buildInstruction();
   }

   private Instruction instrumentMethodReturn(final InstructionSource instructions, final Instruction methodEntry) {
      Instruction cur = methodEntry;
      while(!cur.code().isMethodExit()) {
         if(cur.code().isReturn()) {
            cur.insertHere(
                  statements(instructions).
                  linearOp(new TraceMethodCallOp(callinMatcher, RETURN)).
                  buildInstruction());
         }
         cur = cur.next();
      }
      return methodEntry;
   }

   private Instruction instrumentCallBacks(
         final InstructionSource instructions,
         final Instruction methodEntry) {
      Instruction cur = methodEntry;
      while(!cur.code().isMethodExit()) {
         if(cur.code().isMethodCall()) {
            cur.insertHere(
                  statements(instructions).
                  linearOp(new TraceCallbackCallOp(cur, callbackMatcher)).
                  buildInstruction());
//            cur.insertNext(
//                  statements(instructions).
//                  linearOp(new TraceCallbackReturnOp(matcher, RETURN)).
//                  buildInstruction());
         }
         cur = cur.next();
      }
      return methodEntry;
   }
}
