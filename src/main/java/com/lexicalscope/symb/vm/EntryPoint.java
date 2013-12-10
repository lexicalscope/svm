package com.lexicalscope.symb.vm;

public class EntryPoint {
   private final InstructionNode entryPoint;
   private final int argSize;

   public EntryPoint(final InstructionNode entryPoint, final int argSize) {
      this.entryPoint = entryPoint;
      this.argSize = argSize;
   }

   /**
    * @return the root of the instruction graph, which will be a static method call
    */
   public InstructionNode entryPoint() {
      return entryPoint;
   }

   /**
    * @return the number of arguments the method expects
    */
   public int argSize() {
      return argSize;
   }
}
