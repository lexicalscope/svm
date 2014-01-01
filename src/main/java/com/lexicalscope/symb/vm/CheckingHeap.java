package com.lexicalscope.symb.vm;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.classloader.SClass;


public class CheckingHeap implements Heap {
   private final Heap heap;

   public CheckingHeap(final Heap heap) {
      this.heap = heap;
   }

   @Override
   public Heap snapshot() {
      return new CheckingHeap(heap.snapshot());
   }

   @Override
   public Object newObject(final Allocatable klass) {
      return heap.newObject(klass);
   }

   @Override
   public void put(final Object address, final int offset, final Object val) {
      assert putPreCondition(address, offset, val);
      heap.put(address, offset, val);
   }

   private boolean putPreCondition(final Object address, final int offset, final Object val) {
      final SClass klass = (SClass) heap.get(address, SClass.OBJECT_CLASS_OFFSET);
      if(offset == SClass.OBJECT_CLASS_OFFSET) {
         assert klass == null;
         return true;
      }
      if(klass == null) { return true; }
      if(klass.isArray()) {return true; /* TODO[tim]: check array element type */}
      final Type type = Type.getType(klass.fieldDescAtIndex(offset));
      switch(type.getSort()) {
         case Type.INT:
            assert val instanceof Integer;
            break;
      }
      return true;
   }

   @Override
   public Object get(final Object address, final int offset) {
      return heap.get(address, offset);
   }

   @Override
   public Object nullPointer() {
      return heap.nullPointer();
   }

   @Override
   public int hashCode(final Object address) {
      return heap.hashCode(address);
   }
}
