package com.lexicalscope.svm.partition.trace.symb.search.fakes;


public class FakeStateGoal implements FakeState {
   private final String name;
   private final FakeState successor;

   public FakeStateGoal(final String name, final FakeState successor) {
      this.name = name;
      this.successor = successor;
   }

   public FakeStateGoal(final String name) {
      this(name, null);
   }

   @Override public boolean equals(final Object obj) {
      if (obj != null && obj.getClass().equals(this.getClass())) {
         final FakeStateGoal that = (FakeStateGoal) obj;
         return that.name.equals(this.name) && that.successor.equals(this.successor);
      }
      return false;
   }

   @Override public int hashCode() {
      return name.hashCode();
   }

   @Override public FakeStateGoal execute(final ExecutionStrategy strategy) {
      strategy.goal(successor);
      return this;
   }

   @Override public String toString() {
      return String.format("GOAL(%s)", name);
   }

   public String name() {
      return name;
   }
}
