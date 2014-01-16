package com.lexicalscope.symb.vm.instructions;


public class TerminationException extends RuntimeException {
   private final Object state;

   public TerminationException(final Object state) {
      super(state.toString());
      this.state = state;
   }

   public Object getFinalState() {
      return state;
   }
}
