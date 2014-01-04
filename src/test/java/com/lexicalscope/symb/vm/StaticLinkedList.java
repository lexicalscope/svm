package com.lexicalscope.symb.vm;

import java.util.ArrayList;

public class StaticLinkedList {
   public static int addRemove(final int x) {
      final ArrayList<Integer> list = new ArrayList<>();
      list.add(x);
      return list.remove(0);
   }
}
