package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;
import static com.lexicalscope.svm.partition.trace.Trace.CallReturn.*;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.instrumentation.Instrumentor;
import com.lexicalscope.svm.partition.trace.ops.TraceCallbackCallOp;
import com.lexicalscope.svm.partition.trace.ops.TraceCallbackReturnOp;
import com.lexicalscope.svm.partition.trace.ops.TraceMethodCallOp;
import com.lexicalscope.svm.vm.j.Instruction;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

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

   @Override public Instruction instrument(
         final InstructionSource instructions,
         final SMethodDescriptor method,
         final Instruction methodEntry) {
      return instrumentMethodCallsAndReturns(instructions, method,
                     instrumentMethodCall(instructions, method, methodEntry));
   }

   private Instruction instrumentMethodCall(
         final InstructionSource instructions,
         final SMethodDescriptor methodName,
         final Instruction methodEntry) {
      return statements(instructions).
            before(methodEntry).
            linearOp(new TraceMethodCallOp(callinMatcher, methodName, CALL)).
            buildInstruction();
   }

   private Instruction instrumentMethodCallsAndReturns(
         final InstructionSource instructions,
         final SMethodDescriptor methodName,
         final Instruction methodEntry) {
      for (final Instruction instruction : methodEntry) {
         instruction.query(new InstructionQueryAdapter<Void>(){
            @Override public Void r3turn(final int returnCount) {
               instruction.insertHere(
                     statements(instructions).
                     linearOp(new TraceMethodCallOp(callinMatcher, methodName, RETURN)).
                     buildInstruction());
               return super.r3turn(returnCount);
            }

            private void instrumentMethodCall(final SMethodDescriptor methodName) {
               instruction.insertHere(
                     statements(instructions).
                     linearOp(new TraceCallbackCallOp(methodName, callbackMatcher)).
                     buildInstruction());

               instruction.insertNext(statements(instructions).
                     linearOp(new TraceCallbackReturnOp(methodName)).
                     buildInstruction());
            }

            @Override public Void invokeinterface(final SMethodDescriptor methodName) {
               instrumentMethodCall(methodName);
               return super.invokeinterface(methodName);
            }

            @Override public Void invokespecial(final SMethodDescriptor methodName) {
               instrumentMethodCall(methodName);
               return super.invokespecial(methodName);
            }

            @Override public Void invokevirtual(final SMethodDescriptor methodName) {
               instrumentMethodCall(methodName);
               return super.invokevirtual(methodName);
            }
         });
      }
      return methodEntry;
   }
}
