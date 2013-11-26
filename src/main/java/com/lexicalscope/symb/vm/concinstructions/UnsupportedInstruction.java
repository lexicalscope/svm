package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class UnsupportedInstruction implements Instruction {
   private final AbstractInsnNode abstractInsnNode;

   public UnsupportedInstruction(final AbstractInsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public void eval(final SClassLoader cl, final Vm vm, final State state) {
      throw new UnsupportedInstructionException(abstractInsnNode);
   }

   @Override
	public String toString() {
		return String.format("UNSUPPORTED opcode(%s) type(%s)", abstractInsnNode.getOpcode(), abstractInsnNode.getClass().getSimpleName());
	}
}
