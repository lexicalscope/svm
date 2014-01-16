package com.lexicalscope.symb.vm;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.lexicalscope.symb.vm.instructions.TerminationException;

public class Vm {
   private final Deque<State> pending = new ArrayDeque<>();
   private final Deque<State> finished = new ArrayDeque<>();

   public Vm(final State state) {
      pending.push(state);
   }

   public State execute() {
      while (!pending.isEmpty()) {
         try {
            //System.out.println(pending.peek());
            pending.peek().executeNextInstruction(this);
         } catch (final TerminationException termination) {
            assert pending.peek() == termination.getFinalState();
            finished.push(pending.pop());
            System.out.println("BACKTRACK");
            System.out.println("    TERM " + finished.peek().getMeta());

         }
         catch (final RuntimeException e) {
            System.out.println(pending.peek().trace());
            throw e;
         }
      }
      return result();
   }

   public State result() {
      return finished.peek();
   }

   public void fork(final State[] states) {
      pending.pop();

      System.out.println("FORK");
      for (final State state : states) {
         System.out.println("     " + state.getMeta());
         pending.push(state);
      }
   }

   public Collection<State> results() {
      return finished;
   }
}
