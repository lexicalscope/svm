package com.lexicalscope.svm.partition.trace.symb.search;

import com.lexicalscope.svm.vm.j.State;

public class FakeExecuteTerminal implements FakeExecution {
   private final State state;

   public FakeExecuteTerminal(final State state) {
      this.state = state;
   }
}
