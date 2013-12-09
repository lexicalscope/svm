package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.stackFrameOps.PopOperand;

public final class Ops {
	public static LoadConstants loadConstants(final Object ... values) {
		return new LoadConstants(values);
	}

	public static PopOperand popOperand() {
      return new PopOperand();
   }

   public static Vop nextInstruction(final InstructionNode instruction) {
      return new NextInstructionOp(instruction);
   }

   public static Vop putField(final SClassLoader classLoader, final FieldInsnNode fieldInsnNode) {
      return new PutFieldOp(classLoader.load(fieldInsnNode.owner), fieldInsnNode);
   }

   public static Vop getField(final SClassLoader classLoader, final FieldInsnNode fieldInsnNode) {
      return new GetFieldOp(classLoader.load(fieldInsnNode.owner), fieldInsnNode);
   }

   public static Vop newOp(final SClassLoader classLoader, final TypeInsnNode typeInsnNode) {
      return new NewOp(classLoader, typeInsnNode);
   }

   public static DupOp dup() {
      return new DupOp();
   }
}
