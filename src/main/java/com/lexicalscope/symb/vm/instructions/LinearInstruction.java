package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.StateTransformer;

public class LinearInstruction implements Instruction {
   private final AbstractInsnNode abstractInsnNode;
   private final StateTransformer transformer;

   public LinearInstruction(final AbstractInsnNode abstractInsnNode, final StateTransformer transformer) {
      this.abstractInsnNode = abstractInsnNode;
      this.transformer = transformer;
   }

   @Override
   public void eval(final SClassLoader cl, final Vm vm, final State state) {
      transformer.transform(state, cl.instructionFor(abstractInsnNode.getNext()));
   }

   @Override
   public String toString() {
      return transformer.toString();
   }
}
