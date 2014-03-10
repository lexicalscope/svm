package com.lexicalscope.svm.examples.isort.broken;

import java.util.LinkedList;

public class SortedList {
   private LinkedList<Integer> internal;

   public void add(final int newElement) {
      for (int j = 0; j < internal.size(); j++) {
         if(internal.get(j) > newElement) {
            internal.add(j, newElement);
            return;
         }
      }
      internal.add(newElement);
   }

   public int size() {
      return internal.size();
   }

   public void get(final int i) {
      internal.get(i);
   }
}
