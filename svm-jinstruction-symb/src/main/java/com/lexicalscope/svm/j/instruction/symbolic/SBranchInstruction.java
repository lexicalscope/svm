package com.lexicalscope.svm.j.instruction.symbolic;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import com.lexicalscope.svm.j.instruction.symbolic.predicates.BinarySBranchOp;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.BinarySBranchStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.EqStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.GeStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.GtStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.ICmpEqStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.ICmpGeStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.ICmpGtStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.ICmpLeStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.ICmpLtStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.ICmpNeStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.LeStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.LtStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.NeStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.SBranchStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.UnarySBranchOp;
import com.lexicalscope.svm.j.instruction.symbolic.predicates.UnarySBranchStrategy;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

final class SBranchInstruction implements Vop {
   private final SBranchStrategy branchStrategy;

   SBranchInstruction(final SBranchStrategy branchStrategy) {
      this.branchStrategy = branchStrategy;
   }

   @Override public void eval(final JState ctx) {
      final BoolSymbol branchPredicateSymbol = branchStrategy.evaluateBranchConditonAsSymbol(ctx);

      final JState jumpState;
      final JState nojumpState;

      final boolean willFork = !(branchPredicateSymbol.isTT() || branchPredicateSymbol.isFF());

      if(branchPredicateSymbol.isTT()) {
         jumpState = ctx;
         nojumpState = null;
      } else if(branchPredicateSymbol.isFF()) {
         jumpState = null;
         nojumpState = ctx;
      } else {
         final JState[] states = ctx.fork();
         jumpState = states[0];
         nojumpState = states[1];
      }

      if(jumpState != null) {
         jumpState.advanceTo(ctx.instructionJmpTarget());
      }

      if(nojumpState != null) {
         nojumpState.advanceTo(ctx.instructionNext());
      }

      if(willFork) {
         final BoolSymbol pc = ctx.getMeta(PC);
         jumpState.setMeta(PC, pc.and(branchPredicateSymbol));
         nojumpState.setMeta(PC, pc.and(branchPredicateSymbol.not()));
         ctx.fork(new JState[]{jumpState, nojumpState});
      }
   }

   @Override
   public String toString() {
      return branchStrategy.toString();
   }

   public static Vop geInstruction() {
      return ibranchInstruction(new GeStrategy());
   }

   public static Vop gtInstruction() {
      return ibranchInstruction(new GtStrategy());
   }

   public static Vop leInstruction() {
      return ibranchInstruction(new LeStrategy());
   }

   public static Vop ltInstruction() {
      return ibranchInstruction(new LtStrategy());
   }

   public static Vop neInstruction() {
      return ibranchInstruction(new NeStrategy());
   }

   public static Vop eqInstruction() {
      return ibranchInstruction(new EqStrategy());
   }

   private static Vop ibranchInstruction(final UnarySBranchOp unaryStrategy) {
      return new SBranchInstruction(new UnarySBranchStrategy(unaryStrategy));
   }

   public static Vop icmpgeInstruction() {
      return icmpInstruction(new ICmpGeStrategy());
   }

   public static Vop icmpleInstruction() {
      return icmpInstruction(new ICmpLeStrategy());
   }

   public static Vop icmpneInstruction() {
      return icmpInstruction(new ICmpNeStrategy());
   }

   public static Vop icmpeqInstruction() {
      return icmpInstruction(new ICmpEqStrategy());
   }

   public static Vop icmpgtInstruction() {
      return icmpInstruction(new ICmpGtStrategy());
   }

   public static Vop icmpltInstruction() {
      return icmpInstruction(new ICmpLtStrategy());
   }

   private static Vop icmpInstruction(final BinarySBranchOp icmpStrategy) {
      return new SBranchInstruction(new BinarySBranchStrategy(icmpStrategy));
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.branch();
   }
}