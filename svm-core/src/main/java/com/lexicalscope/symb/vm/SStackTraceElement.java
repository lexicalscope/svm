package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethod;

public class SStackTraceElement {
   private final SMethod method;

   public SStackTraceElement(final SMethod methodName) {
      this.method = methodName;
   }

   public SMethod method() {
      return method;
   }

   @Override public String toString() {
      return "" + method;
   }
}
