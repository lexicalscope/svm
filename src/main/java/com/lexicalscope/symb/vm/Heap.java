package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClass;

public interface Heap extends Snapshotable<Heap> {
   Object newObject(SClass klass);

   void put(Object obj, int offset, Object val);
   Object get(Object obj, int offset);

   @Override
   Heap snapshot();
}