package com.lexicalscope.svm.search2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public class ListStatesCollection implements StatesCollection {
   private final List<JState> states = new ArrayList<>();

   @Override public Iterator<JState> iterator() {
      return states.iterator();
   }

   @Override public void add(final JState state) {
      states.add(state);
   }

   private int size() {
      return states.size();
   }

   @Override public JState remove(final int i) {
      return states.remove(i);
   }

   @Override public boolean isEmpty() {
      return states.isEmpty();
   }

   @Override public int pickState(final Randomiser randomiser) {
      return randomiser.random(size());
   }
}
