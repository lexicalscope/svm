package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class LinearInstruction implements Instruction {
   private final AbstractInsnNode abstractInsnNode;
   private final StateTransformer transformer;

   public LinearInstruction(final AbstractInsnNode abstractInsnNode, final StateTransformer transformer) {
      this.abstractInsnNode = abstractInsnNode;
      this.transformer = transformer;
   }

   @Override
   public void eval(final Vm vm, final State state) {
      transformer.transform(state, instructionFor(abstractInsnNode.getNext()));
   }

   @Override
   public String toString() {
      return transformer.toString();
   }
}
