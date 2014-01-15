package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.InstructionNode;

public final class MethodBody {
   private final InstructionNode entry;
   private final int maxStack;
   private final int maxLocals;

   public MethodBody(final InstructionNode entry, final int maxStack, final int maxLocals) {
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

   public InstructionNode entryPoint() {
      return entry;
   }
}
