package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.InsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class Ireturn implements Instruction {
   private final InsnNode abstractInsnNode;

   public Ireturn(final InsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      return state.discardTop(1);
   }
}
