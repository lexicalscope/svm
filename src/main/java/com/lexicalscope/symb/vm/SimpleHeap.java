package com.lexicalscope.symb.vm;

public final class SimpleHeap implements Heap {
   private final SObject[] objects = new SObject[100];
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
   public void put(final Object obj, final String string, final Object val) {
      objectForRef(obj).put(string, val);
   }

   @Override
   public Object get(final Object obj, final String string) {
      return objectForRef(obj).get(string);
   }

   private SObject objectForRef(final Object obj) {
      return objects[((ObjectRef) obj).address()];
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
