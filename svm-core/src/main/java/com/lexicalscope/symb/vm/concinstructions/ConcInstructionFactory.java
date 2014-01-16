package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.ops.ArrayLoadOp;
import com.lexicalscope.symb.vm.concinstructions.ops.ArrayStoreOp;
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
import com.lexicalscope.symb.vm.instructions.LinearInstruction;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.LoadConstantArg;
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
   public Vop branchIfGe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Ge());
   }

   @Override
   public Vop branchIfGt(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Gt());
   }

   @Override
   public Vop branchIfLe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Le());
   }

   @Override
   public Vop branchIfLt(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Lt());
   }

   @Override
   public Vop branchIfNe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Ne());
   }

   @Override
   public Vop branchIfEq(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Eq());
   }

   @Override public Vop branchIfICmpEq(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpEq());
   }

   @Override public Vop branchIfICmpNe(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpNe());
   }

   @Override public Vop branchIfICmpLe(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpLe());
   }

   @Override public Vop branchIfICmpGe(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpGe());
   }

   @Override public Vop branchIfICmpLt(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpLt());
   }

   @Override public Vop branchIfICmpGt(final JumpInsnNode jumpInsnNode) {
      return icmp(new ICmpGt());
   }

   @Override public Vop branchIfNull(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Null());
   }

   @Override public Vop branchIfNonNull(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new NonNull());
   }

   @Override public Vop branchIfACmpNe(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new ACmpNe());
   }

   @Override public Vop branchIfACmpEq(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new ACmpEq());
   }

   private Vop icmp(final ICmpOp op) {
      return branchInstruction(new ICmpBranchPredicate(op));
   }

   private Vop branchInstruction(final BranchPredicate branchPredicate) {
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

   @Override public Vop aNewArray() {
      return new NewArrayOp(new NewConcArray());
   }

   @Override public Vop aaStore() {
      return ArrayStoreOp.aaStore();
   }

   @Override public Vop iaStore() {
      return ArrayStoreOp.iaStore();
   }

   @Override public Vop iaLoad() {
      return ArrayLoadOp.iaLoad();
   }

   @Override public Vop aaLoad() {
      return ArrayLoadOp.aaLoad();
   }

   @Override public Vop loadArg(final Object object) {
      return new LinearInstruction(new LoadConstantArg(object));
   }
}
