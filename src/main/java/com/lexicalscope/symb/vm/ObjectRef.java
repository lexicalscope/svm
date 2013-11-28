package com.lexicalscope.symb.vm;

public class ObjectRef {
   private final int address;

   public ObjectRef(final int address) {
      this.address = address;
   }

   @Override
   public String toString() {
      return String.format("$%d", address);
   }
}
