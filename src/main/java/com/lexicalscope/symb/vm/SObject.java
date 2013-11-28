package com.lexicalscope.symb.vm;

import java.util.HashMap;

public class SObject {
   // TODO[tim]: need to do something much faster here. Probably need to link the instructions then use a bit-tree
   private final HashMap<String, Object> map = new HashMap<>();

   public void put(final String string, final Object val) {
      map.put(string, val);
   }

   public Object get(final String string) {
      return map.get(string);
   }

   @Override
   public String toString() {
      return map.toString();
   }
}
