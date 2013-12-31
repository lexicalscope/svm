package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

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

   public static Vop putStatic(final FieldInsnNode fieldInsnNode) {
      return new PutStaticOp(fieldInsnNode);
   }

   public static Vop newOp(final String klassDesc) {
      return new VopAdapter(new NewOp(klassDesc));
   }

   public static DupOp dup() {
      return new DupOp();
   }

   public static Dup_X1Op dup_x1() {
      return new Dup_X1Op();
   }
}
