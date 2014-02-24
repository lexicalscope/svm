package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectGoalMap<N> extends AbstractGoalMap<Object, N> {
   private final Map<Object, N> children = new HashMap<>();

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
}
