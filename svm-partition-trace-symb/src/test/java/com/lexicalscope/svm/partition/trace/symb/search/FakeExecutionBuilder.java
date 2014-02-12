package com.lexicalscope.svm.partition.trace.symb.search;

import com.lexicalscope.svm.vm.j.State;

public class FakeExecutionBuilder {
   private final State fromState;
   private State lstate;
   private State rstate;
   private FakeExecutionBuilder lexecution;
   private FakeExecutionBuilder rexecution;
   private ProgramStrategy strategy;

   public FakeExecutionBuilder(final State fromState) {
      this.fromState = fromState;
   }

   public static FakeExecutionBuilder p(final State initialState) {
      final FakeExecutionBuilder builder = new FakeExecutionBuilder(initialState);
      builder.strategy(new PStrategy());
      return builder;
   }

   public static FakeExecutionBuilder q(final State initialState) {
      final FakeExecutionBuilder builder = new FakeExecutionBuilder(initialState);
      builder.strategy(new QStrategy());
      return builder;
   }

   private void strategy(final ProgramStrategy strategy) {
      this.strategy = strategy;
   }

   public static FakeExecutionBuilder from(final State state) {
      return new FakeExecutionBuilder(state);
   }

   public FakeExecutionBuilder to(final State lstate, final State rstate) {
      assert unset();
      this.lstate = lstate;
      this.rstate = rstate;
      return this;
   }

   private boolean unset() {
      return lstate == null && rstate == null && lexecution == null && rexecution == null;
   }

   public FakeExecutionBuilder to(final FakeExecutionBuilder leftExecution, final FakeExecutionBuilder rightExecution) {
      assert unset();
      this.lexecution = leftExecution;
      leftExecution.strategy(strategy);
      this.rexecution = rightExecution;
      rightExecution.strategy(strategy);
      return this;
   }

   public FakeExecution build() {
      if(lstate != null) {
         return new FakeExecuteToBranch(
               new FakeExecuteTerminal(lstate),
               new FakeExecuteTerminal(rstate));
      }
      return new FakeExecuteToBranch(lexecution.build(), rexecution.build());
   }
}
