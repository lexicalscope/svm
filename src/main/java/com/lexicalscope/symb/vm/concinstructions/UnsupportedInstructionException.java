package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.AbstractInsnNode;

public class UnsupportedInstructionException extends RuntimeException {
   public UnsupportedInstructionException(final AbstractInsnNode abstractInsnNode) {
      super("unsupported opcode: " + abstractInsnNode.getOpcode() + " " + abstractInsnNode.getClass().getSimpleName());
   }
}
