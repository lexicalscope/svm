package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Vop;
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

   public static Vop putField(final FieldInsnNode fieldInsnNode) {
      return new PutFieldOp(fieldInsnNode);
   }

   public static Vop getField(final FieldInsnNode fieldInsnNode) {
      return new GetFieldOp(fieldInsnNode);
   }

   public static Vop getStatic(final FieldInsnNode fieldInsnNode) {
      return new GetStaticOp(fieldInsnNode);
   }

   public static Vop newOp(final TypeInsnNode typeInsnNode) {
      return new NewOp(typeInsnNode);
   }

   public static DupOp dup() {
      return new DupOp();
   }
}
