package com.lexicalscope.symb.vm.symbinstructions;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.concinstructions.BranchPredicate;
import com.lexicalscope.symb.vm.concinstructions.LAndOp;
import com.lexicalscope.symb.vm.concinstructions.ops.DConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.FConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.IConstOperator;
import com.lexicalscope.symb.vm.concinstructions.ops.LConstOperator;
import com.lexicalscope.symb.vm.concinstructions.predicates.NonNull;
import com.lexicalscope.symb.vm.concinstructions.predicates.Null;
import com.lexicalscope.symb.vm.instructions.BranchInstruction;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.LinearInstruction;
import com.lexicalscope.symb.vm.instructions.LoadingInstruction;
import com.lexicalscope.symb.vm.instructions.ops.Binary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.LoadConstantArg;
import com.lexicalscope.symb.vm.instructions.ops.Nullary2Operator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.Ops;
import com.lexicalscope.symb.vm.instructions.ops.UnaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp;
import com.lexicalscope.symb.vm.symbinstructions.ops.LoadSymbolicObjectArg;
import com.lexicalscope.symb.vm.symbinstructions.ops.SArrayLoadOp;
import com.lexicalscope.symb.vm.symbinstructions.ops.SArrayStoreOp;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAndOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIBinaryOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIBinaryOperatorAdapter;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIMulOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SISubOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SymbFieldConversionFactory;
import com.lexicalscope.symb.vm.symbinstructions.ops.array.NewSymbArray;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ITerminalSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.OSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.OTerminalSymbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

/**
 * @author tim
 */
public class SymbInstructionFactory implements InstructionFactory {
   private final FeasibilityChecker feasibilityChecker;
   private int symbol = -1;

   public SymbInstructionFactory(final FeasibilityChecker feasbilityChecker) {
      feasibilityChecker = feasbilityChecker;
   }

   public SymbInstructionFactory() {
      this(new FeasibilityChecker());
   }

   @Override
   public BinaryOperator iaddOperation() {
      return siBinary(new SIAddOperator());
   }

   @Override public BinaryOperator iandOperation() {
      return siBinary(new SIAndOperator());
   }

   @Override
   public BinaryOperator imulOperation() {
      return siBinary(new SIMulOperator());
   }

   @Override
   public BinaryOperator isubOperation() {
      return siBinary(new SISubOperator());
   }

   private SIBinaryOperatorAdapter siBinary(final SIBinaryOperator op) {
      return new SIBinaryOperatorAdapter(op);
   }

   @Override public UnaryOperator inegOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   public ITerminalSymbol isymbol() {
      return new ITerminalSymbol(++symbol);
   }

   public OSymbol osymbol(final String klassName) {
      return new OTerminalSymbol(++symbol, klassName);
   }

   @Override
   public Instruction branchIfGe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.geInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfGt(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.gtInstruction(feasibilityChecker);
   }

   @Override
   public Instruction branchIfLe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.leInstruction(feasibilityChecker);
   }

   @Override
   public Instruction branchIfLt(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.ltInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfNe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.neInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfEq(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.eqInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfICmpEq(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.icmpeqInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfICmpNe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.icmpneInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfICmpLe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.icmpleInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfICmpGe(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.icmpgeInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfICmpLt(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.icmpltInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfICmpGt(final JumpInsnNode jumpInsnNode) {
      return SBranchInstruction.icmpgtInstruction(feasibilityChecker);
   }

   @Override public Instruction branchIfACmpEq(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Instruction branchIfACmpNe(final JumpInsnNode jumpInsnNode) {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override
   public NullaryOperator iconst(final int val) {
      return new IConstOperator(val);
   }

   @Override public Nullary2Operator lconst(final long val) {
      return new LConstOperator(val);
   }

   @Override public NullaryOperator fconst(final float val) {
      return new FConstOperator(val);
   }

   @Override
   public Snapshotable<?> initialMeta() {
      return new Pc();
   }

   @Override public Instruction branchIfNonNull(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new NonNull());
   }

   @Override public Instruction branchIfNull(final JumpInsnNode jumpInsnNode) {
      return branchInstruction(new Null());
   }

   private Instruction branchInstruction(final BranchPredicate branchPredicate) {
      return new BranchInstruction(branchPredicate);
   }

   @Override public Object initInt() {
      return 0;
   }

   @Override public BinaryOperator fmulOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public BinaryOperator fdivOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public BinaryOperator faddOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public BinaryOperator fsubOperation() {
      throw new UnsupportedOperationException("not implemented yet");
   }

   @Override public Nullary2Operator dconst(final double val) {
      return new DConstOperator(val);
   }

   @Override public Binary2Operator landOperation() {
      return new LAndOp();
   }

   @Override public Vop putField(final FieldInsnNode fieldInsnNode) {
      return Ops.putField(fieldInsnNode, new SymbFieldConversionFactory());
   }

   @Override public Vop getField(final FieldInsnNode fieldInsnNode) {
      return Ops.getField(fieldInsnNode, new SymbFieldConversionFactory());
   }

   @Override public NewArrayOp newArray(final Object initialFieldValue) {
      return new NewArrayOp(initialFieldValue, new NewSymbArray(feasibilityChecker));
   }

   @Override public Vop aNewArray() {
      return new NewArrayOp(new NewSymbArray(feasibilityChecker));
   }

   @Override public Vop aaStore() {
      return SArrayStoreOp.aaStore(feasibilityChecker);
   }

   @Override public Vop iaStore() {
      return SArrayStoreOp.iaStore(feasibilityChecker);
   }

   @Override public Vop aaLoad() {
      return SArrayLoadOp.aaLoad(feasibilityChecker);
   }

   @Override public Vop iaLoad() {
      return SArrayLoadOp.iaLoad(feasibilityChecker);
   }

   @Override public Instruction loadArg(final Object object) {
      if(object instanceof OTerminalSymbol) {
         final OTerminalSymbol terminalSymbol = (OTerminalSymbol)object;
         return new LoadingInstruction(terminalSymbol.klass(), new LoadSymbolicObjectArg(terminalSymbol));
      } else {
         return new LinearInstruction(new LoadConstantArg(object));
      }
   }
}
