package com.lexicalscope.svm.classloading.linking;

import com.lexicalscope.svm.vm.j.Instruction;

public class LinkedMethod {
   private final int maxLocals;
   private final int maxStack;
   private final Instruction instrumented;

   public LinkedMethod(final int maxLocals, final int maxStack, final Instruction instrument) {
      this.maxLocals = maxLocals;
      this.maxStack = maxStack;
      this.instrumented = instrument;
   }

   public int maxLocals() {
      return maxLocals;
   }

   public int maxStack() {
      return maxStack;
   }

   public Instruction entryPoint() {
      return instrumented;
   }
}
