package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.tree.VarInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class Iload implements Instruction {
   private final VarInsnNode abstractInsnNode;

   public Iload(final VarInsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      return state.load(abstractInsnNode.var).advance(instructionFor(abstractInsnNode.getNext()));
   }

   @Override
   public String toString() {
      return String.format("ILOAD %d", abstractInsnNode.var);
   }
}
