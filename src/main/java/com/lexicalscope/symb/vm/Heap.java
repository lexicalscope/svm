package com.lexicalscope.symb.vm;

public interface Heap {
   Object newObject();

   void put(Object obj, String string, Object val);
   Object get(Object obj, String string);

   Heap snapshot();

}