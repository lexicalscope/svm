package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

final class NextInstructionOp implements StackFrameVop {
   private final SClassLoader cl;
   private final AbstractInsnNode abstractInsnNode;

   NextInstructionOp(final SClassLoader cl, final AbstractInsnNode abstractInsnNode) {
      this.cl = cl;
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public void eval(final StackFrame stackFrame) {
      stackFrame.advance(cl.instructionFor(abstractInsnNode.getNext()));
   }
}