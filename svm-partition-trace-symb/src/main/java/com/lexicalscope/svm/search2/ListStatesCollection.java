package com.lexicalscope.svm.search2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public class ListStatesCollection implements StatesCollection {
   private final List<JState> states = new ArrayList<>();
   private final TraceTreeSideObserver listener;
   private final Randomiser randomiser;

   public ListStatesCollection(
         final Randomiser randomiser,
         final TraceTreeSideObserver listener) {
      this.randomiser = randomiser;
      this.listener = listener;
   }

   @Override public Iterator<JState> iterator() {
      return states.iterator();
   }

   @Override public void add(final JState state) {
      final boolean empty = states.isEmpty();
      states.add(state);
      if(empty) {
         listener.stateAvailable();
      }
   }

   private int size() {
      return states.size();
   }

   private JState remove(final int i) {
      JState result;
      final JState lastItem = states.remove(states.size() - 1);
      if(i == states.size()) {
         result = lastItem;
      }
      else {
         result = states.set(i, lastItem);
      }

      if(isEmpty()) {
         listener.stateUnavailable();
      }
      return result;
   }

   private boolean isEmpty() {
      return states.isEmpty();
   }

   @Override public JState pickState() {
      return remove(randomiser.random(size()));
   }
}
