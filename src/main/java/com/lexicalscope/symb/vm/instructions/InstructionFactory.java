package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.UnaryOperator;

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
   Vop stringPoolLoad(String constVal);

   Vop putField(FieldInsnNode fieldInsnNode);

   Instruction branchIfGe(JumpInsnNode jumpInsnNode);
   Instruction branchIfGt(JumpInsnNode jumpInsnNode);
   Instruction branchIfLe(JumpInsnNode jumpInsnNode);
   Instruction branchIfLt(JumpInsnNode jumpInsnNode);
   Instruction branchIfEq(JumpInsnNode jumpInsnNode);
   Instruction branchIfNe(JumpInsnNode jumpInsnNode);
   Instruction branchIfNull(JumpInsnNode jumpInsnNode);
   Instruction branchIfNonNull(JumpInsnNode jumpInsnNode);
   Instruction branchGoto(JumpInsnNode jumpInsnNode);

   BranchPredicate branchIfICmpEq(JumpInsnNode jumpInsnNode);
   BranchPredicate branchIfICmpNe(JumpInsnNode jumpInsnNode);
   BranchPredicate branchIfICmpLe(JumpInsnNode jumpInsnNode);
   BranchPredicate branchIfICmpGe(JumpInsnNode jumpInsnNode);
   BranchPredicate branchIfICmpLt(JumpInsnNode jumpInsnNode);
   BranchPredicate branchIfICmpGt(JumpInsnNode jumpInsnNode);

   Instruction branchIfACmpEq(JumpInsnNode jumpInsnNode);
   Instruction branchIfACmpNe(JumpInsnNode jumpInsnNode);

   Snapshotable<?> initialMeta();

   // initial values for fields
   Object initInt();
}
