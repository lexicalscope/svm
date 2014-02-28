package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.lexicalscope.svm.search.Randomiser;

public final class ObjectGoalMap<N> extends AbstractGoalMap<Object, N> {
   private final LinkedHashMap<Object, N> childMap = new LinkedHashMap<>();
   private final List<N> list = new ArrayList<>();

   @Override public N get(final Object goal, final SubtreeFactory<N> factory) {
      N child = childMap.get(goal);
      if(child == null) {
         child = factory.create();
         childMap.put(goal, child);
      }
      return child;
   }

   @Override public void put(final Object goal, final N node) {
      list.add(node);
      childMap.put(goal, node);
   }

   @Override public boolean isEmpty() {
      return childMap.isEmpty();
   }

   @Override public Iterator<N> iterator() {
      return childMap.values().iterator();
   }

   @Override public int size() {
      return childMap.size();
   }

   @Override public boolean containsGoal(final Object goal) {
      return childMap.containsKey(goal);
   }

   @Override public N get(final Object goal) {
      return childMap.get(goal);
   }

   @Override public N getRandom(final Randomiser randomiser) {
      return list.get(randomiser.random(list.size()));
   }
}
