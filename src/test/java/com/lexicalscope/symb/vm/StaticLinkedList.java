package com.lexicalscope.symb.vm;

import java.util.LinkedList;

public class StaticLinkedList {
   public static int addRemove(final int x) {
      final LinkedList<Integer> list = new LinkedList<>();
      list.add(x);
      return list.removeFirst();
   }
}
