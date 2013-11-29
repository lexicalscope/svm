package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.stackFrameOps.PopOperand;

public final class Ops {
	public static LoadConstants loadConstants(final Object ... values) {
		return new LoadConstants(values);
	}

	public static PopOperand popOperand() {
      return new PopOperand();
   }

   public static StackFrameVop nextInstruction(final SClassLoader cl, final AbstractInsnNode abstractInsnNode) {
      return new NextInstructionOp(cl, abstractInsnNode);
   }

   public static HeapVop putField(final FieldInsnNode fieldInsnNode) {
      return new PutFieldOp(fieldInsnNode);
   }

   public static HeapVop getField(final FieldInsnNode fieldInsnNode) {
      return new GetFieldOp(fieldInsnNode);
   }

   public static HeapVop newOp(final TypeInsnNode typeInsnNode) {
      return new NewOp(typeInsnNode);
   }

   public static DupOp dup() {
      return new DupOp();
   }
}
