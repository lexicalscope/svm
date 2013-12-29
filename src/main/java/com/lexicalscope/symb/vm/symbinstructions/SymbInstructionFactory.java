package com.lexicalscope.symb.vm.symbinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.UnaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIConstOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIMulOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SISubOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

/**
 * @author tim
 */
public class SymbInstructionFactory implements InstructionFactory {
   final FeasibilityChecker feasibilityChecker = new FeasibilityChecker();
   private int symbol = -1;

   @Override
   public BinaryOperator iaddOperation() {
      return new SIAddOperator();
   }

   @Override
   public BinaryOperator imulOperation() {
      return new SIMulOperator();
   }

   @Override
   public BinaryOperator isubOperation() {
      return new SISubOperator();
   }

   @Override public UnaryOperator inegOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   public Symbol symbol() {
      return new ISymbol(++symbol);
   }

   @Override
   public Instruction branchIfGe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.geInstruction(feasibilityChecker);
   }

   @Override
   public Instruction branchIfLe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override
   public Instruction branchIfLt(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfNe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfEq(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchGoto(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpEq(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpNe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpLe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpGe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpLt(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfICmpGt(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfACmpEq(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfACmpNe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override
   public NullaryOperator iconst(final int val) {
      return new SIConstOperator(val);
   }

   @Override public Nullary2Operator lconst(final long val) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public NullaryOperator fconst(final float val) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Vop stringPoolLoad(final String constVal) {
      throw new UnsupportedOperationException("unable to handle symbolic strings yet");
   }

   @Override
   public Snapshotable<?> initialMeta() {
      return new Pc();
   }

   @Override public Instruction branchIfNonNull(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("unable to handle symbolic object yet");
   }

   @Override public Object initInt() {
      return new IConstSymbol(0);
   }

   @Override public BinaryOperator fmulOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public BinaryOperator iandOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Nullary2Operator dconst(final double val) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Binary2Operator landOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfGt(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }
}
