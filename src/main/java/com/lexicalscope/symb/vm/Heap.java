package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.Allocatable;

public interface Heap extends Snapshotable<Heap> {
   Object newObject(Allocatable klass);

   void put(Object obj, int offset, Object val);
   Object get(Object obj, int offset);

   Object nullPointer();
}