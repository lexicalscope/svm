package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class Return implements Instruction {
   private final AbstractInsnNode abstractInsnNode;

   public Return(final AbstractInsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      return state.discardTop();
   }
}
