package com.lexicalscope.symb.vm;

public class SimpleHeap implements Heap {
   private final Object[] objects = new Object[100];
   private int free = 0;

	@Override
   public Heap snapshot() {
		return new SimpleHeap();
	}

   @Override
   public ObjectRef newObject() {
      objects[free] = new SObject();
      return new ObjectRef(free++);
   }

   @Override
   public String toString() {
      final StringBuilder result = new StringBuilder();
      for (int i = 0; i < free; i++) {
         result.append("$").append(i).append("=").append(objects[i]);
      }
      return result.toString();
   }
}
