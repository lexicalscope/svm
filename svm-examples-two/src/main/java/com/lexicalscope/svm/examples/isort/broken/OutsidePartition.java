package com.lexicalscope.svm.examples.isort.broken;


public class OutsidePartition {
   public void entry(final int[] ints) {
      final SortedList sortedList = new SortedList();
      for (final int i : ints) {
         sortedList.add(i);
      }
      for (int i = 0; i < sortedList.size(); i++) {
         sortedList.get(i);
      }
   }

   public static void callSomeMethods(final int[] ints) {
      new OutsidePartition().entry(ints);
   }
}