package com.lexicalscope.svm.partition.trace.symb.search.fakes;

public interface ExecutionStrategy {
   void backtrack();

   void fork(FakeState left, FakeState right);
}