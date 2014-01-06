package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.ConcFieldConversionFactory;
import com.lexicalscope.symb.vm.concinstructions.ops.DConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.FAddOp;
import com.lexicalscope.symb.vm.concinstructions.ops.FConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.FDivOp;
import com.lexicalscope.symb.vm.concinstructions.ops.FMulOp;
import com.lexicalscope.symb.vm.concinstructions.ops.FSubOp;
import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.concinstructions.ops.IConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.IMulOp;
import com.lexicalscope.symb.vm.concinstructions.ops.INegOp;
import com.lexicalscope.symb.vm.concinstructions.ops.ISubOp;
import com.lexicalscope.symb.vm.concinstructions.ops.LConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.StringPoolLoadOperator;
import com.lexicalscope.symb.vm.concinstructions.predicates.ACmpEq;
import com.lexicalscope.symb.vm.concinstructions.predicates.ACmpNe;
import com.lexicalscope.symb.vm.concinstructions.predicates.Eq;
import com.lexicalscope.symb.vm.concinstructions.predicates.Ge;
import com.lexicalscope.symb.vm.concinstructions.predicates.Gt;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpBranchPredicate;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpEq;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpGe;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpGt;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpLe;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpLt;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpNe;
import com.lexicalscope.symb.vm.concinstructions.predicates.ICmpOp;
import com.lexicalscope.symb.vm.concinstructions.predicates.Le;
import com.lexicalscope.symb.vm.concinstructions.predicates.Lt;
import com.lexicalscope.symb.vm.concinstructions.predicates.Ne;
import com.lexicalscope.symb.vm.concinstructions.predicates.NonNull;
import com.lexicalscope.symb.vm.concinstructions.predicates.Null;
import com.lexicalscope.symb.vm.instructions.BranchInstruction;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Ops;
import com.lexicalscope.symb.vm.instructions.ops.UnaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp;
import com.lexicalscope.symb.vm.instructions.ops.array.NewConcArray;

public class ConcInstructionFactory implements InstructionFactory {
   @Override public BinaryOperator iaddOperation() {
      return new IAddOp();
   }

   @Override
   public BinaryOperator imulOperation() {
      return new IMulOp();
   }

   @Override
   public BinaryOperator fmulOperation() {
      return new FMulOp();
   }

   @Override
   public BinaryOperator fdivOperation() {
      return new FDivOp();
   }

   @Override
   public BinaryOperator fsubOperation() {
      return new FSubOp();
   }

   @Override
   public BinaryOperator faddOperation() {
      return new FAddOp();
   }

   @Override
   public BinaryOperator isubOperation() {
      return new ISubOp();
   }

   @Override
   public UnaryOperator inegOperation() {
      return new INegOp();
   }

   @Override public BinaryOperator iandOperation() {
      return new IAndOp();
   }

   @Override public Binary2Operator landOperation() {
      return new LAndOp();
   }

   @Override
   public Instruction branchIfGe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Ge());
   }

   @Override
   public Instruction branchIfGt(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Gt());
   }

   @Override
   public Instruction branchIfLe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Le());
   }

   @Override
   public Instruction branchIfLt(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Lt());
   }

   @Override
   public Instruction branchIfNe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Ne());
   }

   @Override
   public Instruction branchIfEq(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Eq());
   }

   @Override public Instruction branchIfICmpEq(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpEq());
   }

   @Override public Instruction branchIfICmpNe(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpNe());
   }

   @Override public Instruction branchIfICmpLe(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpLe());
   }

   @Override public Instruction branchIfICmpGe(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpGe());
   }

   @Override public Instruction branchIfICmpLt(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpLt());
   }

   @Override public Instruction branchIfICmpGt(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpGt());
   }

   @Override public Instruction branchIfNull(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Null());
   }

   @Override public Instruction branchIfNonNull(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new NonNull());
   }

   @Override public Instruction branchIfACmpNe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new ACmpNe());
   }

   @Override public Instruction branchIfACmpEq(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new ACmpEq());
   }

   private Instruction icmp(final ICmpOp op) {
      return branchInstruction(new ICmpBranchPredicate(op));
   }

   private Instruction branchInstruction(final BranchPredicate branchPredicate) {
      return new BranchInstruction(branchPredicate);
   }

   @Override
   public NullaryOperator iconst(final int val) {
      return new IConstOperator(val);
   }

   @Override
   public Nullary2Operator lconst(final long val) {
      return new LConstOperator(val);
   }

   @Override
   public NullaryOperator fconst(final float val) {
      return new FConstOperator(val);
   }

   @Override
   public Nullary2Operator dconst(final double val) {
      return new DConstOperator(val);
   }

   @Override public Vop stringPoolLoad(final String val) {
      return new StringPoolLoadOperator(val);
   }

   @Override
   public Snapshotable<?> initialMeta() {
      return null;
   }

   @Override public Object initInt() {
      return 0;
   }

   @Override public Vop putField(final FieldInsnNode fieldInsnNode) {
      return Ops.putField(fieldInsnNode, new ConcFieldConversionFactory());
   }

   @Override public Vop getField(final FieldInsnNode fieldInsnNode) {
      return Ops.getField(fieldInsnNode, new ConcFieldConversionFactory());
   }

   @Override public NewArrayOp newArray(final Object initialFieldValue) {
      return new NewArrayOp(initialFieldValue, new NewConcArray());
   }
}
