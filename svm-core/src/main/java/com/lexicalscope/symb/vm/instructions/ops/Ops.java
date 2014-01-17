package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.FieldConversionFactory;
import com.lexicalscope.symb.vm.stackFrameOps.PopOperand;

public final class Ops {
   public static PopOperand popOperand() {
      return new PopOperand();
   }

   public static Vop advanceTo(final Instruction instruction) {
      return new AdvanceToInstructionOp(instruction);
   }

   public static Vop putField(final FieldInsnNode fieldInsnNode, final FieldConversionFactory fieldConversionFactory) {
      return new PutFieldOp(fieldConversionFactory, fieldInsnNode);
   }

   public static Vop getField(final FieldInsnNode fieldInsnNode, final FieldConversionFactory fieldConversionFactory) {
      return new GetFieldOp(fieldConversionFactory, fieldInsnNode);
   }

   public static Vop getStatic(final FieldInsnNode fieldInsnNode) {
      return new GetStaticOp(fieldInsnNode);
   }

   public static Vop putStatic(final FieldInsnNode fieldInsnNode) {
      return new PutStaticOp(fieldInsnNode);
   }

   public static Vop newOp(final String klassDesc) {
      return new VopAdapter(new NewObjectOp(klassDesc));
   }

   public static DupOp dup() {
      return new DupOp();
   }

   public static Dup_X1Op dup_x1() {
      return new Dup_X1Op();
   }
}
