package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOperator;
import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.j.Vop;

public interface InstructionFactory {
   BinaryOperator iaddOperation();
   BinaryOperator imulOperation();
   BinaryOperator isubOperation();
   BinaryOperator iandOperation();
   UnaryOperator inegOperation();

   Binary2Operator landOperation();

   BinaryOperator fmulOperation();
   BinaryOperator fdivOperation();
   BinaryOperator faddOperation();
   BinaryOperator fsubOperation();

   NullaryOperator iconst(int val);
   Nullary2Operator lconst(long val);
   NullaryOperator fconst(float val);
   Nullary2Operator dconst(double val);

   Vop newArray(Object initialFieldValue);
   Vop aNewArray();
   Vop reflectionNewArray();
   Vop aaStore();
   Vop iaStore();

   Vop iaLoad();
   Vop aaLoad();

   Vop putField(FieldInsnNode fieldInsnNode);
   Vop getField(FieldInsnNode fieldInsnNode);

   Vop branchIfGe(JumpInsnNode jumpInsnNode);
   Vop branchIfGt(JumpInsnNode jumpInsnNode);
   Vop branchIfLe(JumpInsnNode jumpInsnNode);
   Vop branchIfLt(JumpInsnNode jumpInsnNode);
   Vop branchIfEq(JumpInsnNode jumpInsnNode);
   Vop branchIfNe(JumpInsnNode jumpInsnNode);
   Vop branchIfNull(JumpInsnNode jumpInsnNode);
   Vop branchIfNonNull(JumpInsnNode jumpInsnNode);

   Vop branchIfICmpEq(JumpInsnNode jumpInsnNode);
   Vop branchIfICmpNe(JumpInsnNode jumpInsnNode);
   Vop branchIfICmpLe(JumpInsnNode jumpInsnNode);
   Vop branchIfICmpGe(JumpInsnNode jumpInsnNode);
   Vop branchIfICmpLt(JumpInsnNode jumpInsnNode);
   Vop branchIfICmpGt(JumpInsnNode jumpInsnNode);

   Vop branchIfACmpEq(JumpInsnNode jumpInsnNode);
   Vop branchIfACmpNe(JumpInsnNode jumpInsnNode);

   Vop loadArg(Object object, InstructionSource instructions);

   Snapshotable<?> initialMeta();

   // initial values for fields
   Object initInt();
}
