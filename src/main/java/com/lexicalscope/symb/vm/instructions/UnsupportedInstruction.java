package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class UnsupportedInstruction implements Instruction {
   private final AbstractInsnNode abstractInsnNode;

   public UnsupportedInstruction(final AbstractInsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      throw new UnsupportedInstructionException(abstractInsnNode);
   }
}
