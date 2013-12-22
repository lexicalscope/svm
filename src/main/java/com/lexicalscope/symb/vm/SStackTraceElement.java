package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethodName;

public class SStackTraceElement {
   private SMethodName methodName;

   public SStackTraceElement(SMethodName methodName) {
      this.methodName = methodName;
   }

   public SMethodName name() {
      return methodName;
   }
   
   @Override public String toString() {
      return "" + methodName;
   }
}
