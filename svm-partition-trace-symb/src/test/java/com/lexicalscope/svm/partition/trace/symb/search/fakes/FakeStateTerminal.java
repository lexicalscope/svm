package com.lexicalscope.svm.partition.trace.symb.search.fakes;


public class FakeStateTerminal implements FakeState {
   private final String name;

   public FakeStateTerminal(final String name) {
      this.name = name;
   }

   @Override public boolean equals(final Object obj) {
      return   obj != null
            && obj.getClass().equals(this.getClass())
            && ((FakeStateTerminal) obj).name.equals(this.name);
   }

   @Override public int hashCode() {
      return name.hashCode();
   }

   @Override public FakeStateTerminal execute(final ExecutionStrategy strategy) {
      strategy.backtrack();
      return this;
   }

   @Override public String toString() {
      return String.format("TERM(%s)", name);
   }
}
