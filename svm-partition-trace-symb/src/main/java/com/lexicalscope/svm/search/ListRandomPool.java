package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListRandomPool<T> {
   private final List<T> list = new ArrayList<>();

   public ListRandomPool() { }

   public ListRandomPool(final Collection<T> children) {
      list.addAll(children);
   }

   public void add(final T child) {
      list.add(child);
   }

   public boolean isEmpty() {
      return list.isEmpty();
   }

   public T randomElement(final Randomiser randomiser) {
      final int index = randomiser.random(list.size());
      final T result = list.get(index);
      list.set(index, list.get(list.size() - 1));
      list.remove(list.size() - 1);
      return result;
   }
}
