package com.lexicalscope.svm.partition.trace.symb.search.fakes;


public class FakeExecutionBuilder {
   public static FakeState term(final String name) {
      return new FakeStateTerminal(name);
   }

   public static FakeExecuteToBranch branch(final FakeState leftSuccessor, final FakeState rightSuccessor) {
      return new FakeExecuteToBranch(leftSuccessor, rightSuccessor);
   }
}
