package com.lexicalscope.symb.heap;

import com.lexicalscope.symb.state.Snapshotable;


public interface Heap extends Snapshotable<Heap> {
   Object newObject(Allocatable klass);

   void put(Object address, int offset, Object val);
   Object get(Object address, int offset);

   Object nullPointer();

   /**
    * Get a hashCode for the given address
    */
   int hashCode(Object address);
}