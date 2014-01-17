package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class UnsupportedInstruction implements Vop {
   private final AbstractInsnNode abstractInsnNode;

   public UnsupportedInstruction(final AbstractInsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
	public String toString() {
		return String.format("UNSUPPORTED opcode(%s) type(%s)", abstractInsnNode.getOpcode(), abstractInsnNode.getClass().getSimpleName());
	}

   @Override public void eval(final StateImpl ctx) {
      throw new UnsupportedInstructionException(abstractInsnNode);
   }
}
