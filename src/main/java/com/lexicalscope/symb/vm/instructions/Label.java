package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.LabelNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class Label implements Instruction {
   private final LabelNode abstractInsnNode;

   public Label(final LabelNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public void eval(final Vm vm, final State state) {
      new NextInstruction().next(vm, state, abstractInsnNode);
   }
}
