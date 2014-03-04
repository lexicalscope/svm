package com.lexicalscope.svm.partition.trace.symb.search;

import com.lexicalscope.svm.vm.VmState;

public class FakeVmState implements VmState {
   private final String string;

   public FakeVmState() {
      this("");
   }

   public FakeVmState(final String string) {
      this.string = string;
   }

   @Override public void eval() {
      // does nothing
   }

   @Override public String toString() {
      if(string.isEmpty()) {
         return super.toString();
      }
      return String.format("(state %s)", string);
   }
}
