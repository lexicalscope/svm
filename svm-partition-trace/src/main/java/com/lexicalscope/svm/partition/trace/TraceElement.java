package com.lexicalscope.svm.partition.trace;

import static com.lexicalscope.svm.partition.trace.Trace.CallReturn.CALL;

import java.util.Arrays;

import com.google.common.base.Joiner;
import com.lexicalscope.svm.partition.trace.Trace.CallReturn;
import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class TraceElement {
   private final TraceElement previous;
   private final SMethodDescriptor method;
   private final CallReturn callReturn;
   private final Object[] args;

   TraceElement(
         final TraceElement previous,
         final SMethodDescriptor method,
         final CallReturn callReturn,
         final Object[] args) {
      assert !Arrays.asList(args).contains(null);
      this.previous = previous;
      this.method = method;
      this.callReturn = callReturn;
      this.args = args;
   }

   public TraceElement previous() {
      return previous;
   }

   public SMethodName methodName() {
      return method;
   }

   public boolean isCall() {
      return callReturn.equals(CALL);
   }

   public Object[] args() {
      return args;
   }

   @Override public String toString() {
      return String.format("[%s]%s - (%s)", callReturn, method, Joiner.on(", ").join(args));
   }
}
