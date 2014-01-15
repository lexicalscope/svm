package com.lexicalscope.symb.vm;


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
