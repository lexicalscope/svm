package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.State;

public class TerminationException extends RuntimeException {
   private final State state;

   public TerminationException(final State state) {
      super(state.toString());
      this.state = state;
   }

   public State getFinalState() {
      return state;
   }
}
