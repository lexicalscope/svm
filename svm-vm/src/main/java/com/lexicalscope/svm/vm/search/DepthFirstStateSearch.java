package com.lexicalscope.svm.vm.search;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import com.lexicalscope.svm.vm.FlowNode;
import com.lexicalscope.svm.vm.StateSearch;

public class DepthFirstStateSearch<S> implements StateSearch<S> {
   private final Deque<FlowNode<S>> pending = new ArrayDeque<>();
   private final Deque<FlowNode<S>> finished = new ArrayDeque<>();

   @Override
   public FlowNode<S> firstResult() {
      return finished.isEmpty() ? null : finished.peek();
   }

   @Override
   public void reachedLeaf() {
      finished.push(pending.pop());
   }

   @Override
   public FlowNode<S> pendingState() {
      return pending.peek();
   }

   @Override
   public void initial(final FlowNode<S> state) {
      pending.push(state);
   }

   @Override
   public void fork(final FlowNode<S>[] states) {
      pending.pop();

      for (final FlowNode<S> state : states) {
         pending.push(state);
      }
   }

   @Override
   public Collection<FlowNode<S>> results() {
      return finished;
   }

   @Override public void goal() {
      // nothing
   }
}