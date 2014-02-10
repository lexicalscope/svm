package com.lexicalscope.svm.vm.conc.checkingheap;

import org.objectweb.asm.Type;

import com.lexicalscope.svm.heap.Allocatable;
import com.lexicalscope.svm.heap.Heap;
import com.lexicalscope.svm.vm.j.StaticsMarker;
import com.lexicalscope.svm.vm.j.klass.SClass;


public class CheckingHeap implements Heap {
   private final Heap heap;
   private final HeapCheck heapCheck;

   public CheckingHeap(final Heap heap, final HeapCheck heapCheck) {
      this.heap = heap;
      this.heapCheck = heapCheck;
   }

   @Override
   public Heap snapshot() {
      return new CheckingHeap(heap.snapshot(), heapCheck);
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
      final Object marker = heap.get(address, SClass.OBJECT_MARKER_OFFSET);
      if(offset == SClass.OBJECT_MARKER_OFFSET) {
         assert marker == null;
         return true;
      }
      if(marker instanceof SClass) {
         final SClass klass = (SClass) marker;
         if(klass == null) { return true; }
         if(klass.isArray()) {return true; /* TODO[tim]: check array element type */}
         if(klass.isKlassKlass()) {return true; /* TODO[tim]: check static fields */}
         final Type type = Type.getType(klass.fieldAtIndex(offset).desc());
         switch(type.getSort()) {
            case Type.INT:
               assert heapCheck.allowedInIntField(val);
               break;
         }
         return true;
      } else if (marker instanceof StaticsMarker) {
         return true;
      } else {
         return false;
      }
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

   @Override public String toString() {
      return heap.toString();
   }
}
