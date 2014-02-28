package com.lexicalscope.svm.vm.search;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.lexicalscope.svm.vm.StateSearch;

public class DepthFirstStateSearch<S> implements StateSearch<S> {
   private final Deque<S> pending = new ArrayDeque<>();
   private final Deque<S> finished = new ArrayDeque<>();

   @Override
   public S firstResult() {
      return finished.isEmpty() ? null : finished.peek();
   }

   @Override
   public void reachedLeaf() {
      finished.push(pending.pop());
   }

   @Override
   public S pendingState() {
      return pending.peek();
   }

   @Override
   public void fork(final S[] states) {
      pending.pop();
      for (final S state : states) {
         pending.push(state);
      }
   }

   @Override
   public void consider(final S state) {
      pending.push(state);
   }

   @Override
   public Collection<S> results() {
      return finished;
   }

   @Override public void goal() {
      // nothing
   }
}