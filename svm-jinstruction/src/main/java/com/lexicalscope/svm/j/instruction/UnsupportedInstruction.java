package com.lexicalscope.svm.j.instruction;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public class UnsupportedInstruction implements Vop {
   private final AbstractInsnNode abstractInsnNode;

   public UnsupportedInstruction(final AbstractInsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
	public String toString() {
		return String.format("UNSUPPORTED opcode(%s) type(%s)", abstractInsnNode.getOpcode(), abstractInsnNode.getClass().getSimpleName());
	}

   @Override public void eval(final State ctx) {
      throw new UnsupportedInstructionException(abstractInsnNode);
   }
}
