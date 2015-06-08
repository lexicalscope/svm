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
      final JState result = removeWithSwap(i, states);

      if(isEmpty()) {
         listener.stateUnavailable();
      }
      return result;
   }

   public static <T> T removeWithSwap(final int i, final List<T> list) {
      final T lastItem = list.remove(list.size() - 1);
      if(i == list.size()) {
         return lastItem;
      }
      else {
         return list.set(i, lastItem);
      }
   }

   private boolean isEmpty() {
      return states.isEmpty();
   }

   @Override public JState pickState() {
      return remove(randomiser.random(size()));
   }
}
