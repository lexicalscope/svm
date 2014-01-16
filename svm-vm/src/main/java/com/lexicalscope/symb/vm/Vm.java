package com.lexicalscope.symb.vm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

public final class Vm<S extends VmVop<S>> {
   final Deque<S> pending = new ArrayDeque<>();
   final Deque<S> finished = new ArrayDeque<>();

   public Vm(final S state) {
      pending.push(state);
   }

   public S execute() {
      while (!pending.isEmpty()) {
         try {
            pending.peek().eval(this);
         } catch (final TerminationException termination) {
            assert pending.peek() == termination.getFinalState();
            finished.push(pending.pop());
         }
         catch (final RuntimeException e) {
            throw e;
         }
      }
      return result();
   }

   public void fork(final S[] states) {
      pending.pop();

      for (final S state : states) {
         pending.push(state);
      }
   }

   public S result() {
      return finished.peek();
   }

   public Collection<S> results() {
      return finished;
   }
}
