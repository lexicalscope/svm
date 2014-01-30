package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.LoadConstantArg;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayLoadOp;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayStoreOp;
import com.lexicalscope.svm.j.instruction.concrete.array.NewArrayOp;
import com.lexicalscope.svm.j.instruction.concrete.array.NewConcArray;
import com.lexicalscope.svm.j.instruction.concrete.branch.BranchInstruction;
import com.lexicalscope.svm.j.instruction.concrete.branch.BranchPredicate;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ACmpEq;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ACmpNe;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Eq;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Ge;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Gt;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpBranchPredicate;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpEq;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpGe;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpGt;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpLe;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpLt;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpNe;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.ICmpOp;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Le;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Lt;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Ne;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.NonNull;
import com.lexicalscope.svm.j.instruction.concrete.branchPredicates.Null;
import com.lexicalscope.svm.j.instruction.concrete.d0uble.DConstOperator;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FAddOp;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FConstOperator;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FDivOp;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FMulOp;
import com.lexicalscope.svm.j.instruction.concrete.fl0at.FSubOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IAddOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IAndOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.IConstOperator;
import com.lexicalscope.svm.j.instruction.concrete.integer.IMulOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.INegOp;
import com.lexicalscope.svm.j.instruction.concrete.integer.ISubOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LAndOp;
import com.lexicalscope.svm.j.instruction.concrete.l0ng.LConstOperator;
import com.lexicalscope.svm.j.instruction.concrete.object.ConcFieldConversionFactory;
import com.lexicalscope.svm.j.instruction.concrete.object.GetFieldOp;
import com.lexicalscope.svm.j.instruction.concrete.object.PutFieldOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOperator;
import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.j.Vop;

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
      return new PutFieldOp(new ConcFieldConversionFactory(), fieldInsnNode);
   }

   @Override public Vop getField(final FieldInsnNode fieldInsnNode) {
      return new GetFieldOp(new ConcFieldConversionFactory(), fieldInsnNode);
   }

   @Override public NewArrayOp newArray(final Object initialFieldValue) {
      return new NewArrayOp(initialFieldValue, new NewConcArray());
   }

   @Override public Vop aNewArray() {
      return new NewArrayOp(new NewConcArray());
   }

   @Override public Vop reflectionNewArray() {
      // TODO[tim]: take into account array type
      return aNewArray();
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

   @Override public Vop loadArg(final Object object, final InstructionSource instructions) {
      return new LinearInstruction(new LoadConstantArg(object));
   }
}
