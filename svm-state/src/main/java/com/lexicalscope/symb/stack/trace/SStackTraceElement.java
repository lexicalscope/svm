package com.lexicalscope.symb.stack.trace;



public class SStackTraceElement {
   private final SMethodName method;

   public SStackTraceElement(final SMethodName methodName) {
      this.method = methodName;
   }

   public SMethodName method() {
      return method;
   }

   @Override public String toString() {
      return "" + method;
   }
}
