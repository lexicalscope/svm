package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ObjectGoalMap<N> extends AbstractGoalMap<Object, N> {
   private final Map<Object, N> children = new HashMap<>();

   @Override public N get(final Object goal, final SubtreeFactory<N> factory) {
      N child = children.get(goal);
      if(child == null) {
         child = factory.create();
         children.put(goal, child);
      }
      return child;
   }

   @Override public void put(final Object goal, final N node) {
      children.put(goal, node);
   }

   @Override public boolean isEmpty() {
      return children.isEmpty();
   }

   @Override public Iterator<N> iterator() {
      return children.values().iterator();
   }

   @Override public int size() {
      return children.size();
   }

   @Override public boolean containsGoal(final Object goal) {
      return children.containsKey(goal);
   }

   @Override public N get(final Object goal) {
      return children.get(goal);
   }
}
