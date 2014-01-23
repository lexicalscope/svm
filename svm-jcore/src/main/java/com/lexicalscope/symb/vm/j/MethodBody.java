package com.lexicalscope.symb.vm.j;


public final class MethodBody {
   private final Instruction entry;
   private final int maxStack;
   private final int maxLocals;

   public MethodBody(final Instruction entry, final int maxStack, final int maxLocals) {
      this.entry = entry;
      this.maxStack = maxStack;
      this.maxLocals = maxLocals;
   }

   public int maxLocals() {
      return maxLocals;
   }

   public int maxStack() {
      return maxStack;
   }

   public Instruction entryPoint() {
      return entry;
   }
}
