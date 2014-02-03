package com.lexicalscope.svm.j.instruction.symbolic;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import com.lexicalscope.svm.j.instruction.symbolic.pc.Pc;
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
import com.lexicalscope.svm.j.instruction.symbolic.symbols.NotSymbol;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.z3.FeasibilityChecker;

final class SBranchInstruction implements Vop {
   private final FeasibilityChecker feasibilityChecker;
   private final SBranchStrategy branchStrategy;

   SBranchInstruction(
         final FeasibilityChecker feasibilityChecker,
         final SBranchStrategy branchStrategy
         ) {
      this.feasibilityChecker = feasibilityChecker;
      this.branchStrategy = branchStrategy;
   }

   @Override public void eval(final State ctx) {
      final Pc pc = ctx.getMeta(PC);

      final BoolSymbol jumpSymbol = branchStrategy.branchPredicateSymbol(ctx);

      final Pc jumpPc = pc.snapshot().and(jumpSymbol);
      final boolean jumpFeasible = feasibilityChecker.check(jumpPc);

      final NotSymbol nojumpSymbol = new NotSymbol(jumpSymbol);
      final Pc nojumpPc = pc.snapshot().and(nojumpSymbol);
      final boolean nojumpFeasible = feasibilityChecker.check(nojumpPc);

      if(jumpFeasible && nojumpFeasible)
      {
         final State[] states = ctx.fork();

         // jump
         states[0].setMeta(PC, jumpPc);
         states[0].advanceTo(ctx.instructionJmpTarget());

         // no jump
         states[1].setMeta(PC, nojumpPc);
         states[1].advanceTo(ctx.instructionNext());

         ctx.fork(states);
      } else if(jumpFeasible) {
         ctx.advanceTo(ctx.instructionJmpTarget());
      } else if(nojumpFeasible) {
         ctx.advanceTo(ctx.instructionNext());
      } else {
         throw new RuntimeException("unable to check feasibility");
      }
   }

   @Override
   public String toString() {
      return branchStrategy.toString();
   }

   public static Vop geInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new GeStrategy());
   }

   public static Vop gtInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new GtStrategy());
   }

   public static Vop leInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new LeStrategy());
   }

   public static Vop ltInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new LtStrategy());
   }

   public static Vop neInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new NeStrategy());
   }

   public static Vop eqInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new EqStrategy());
   }

   private static Vop ibranchInstruction(final FeasibilityChecker feasibilityChecker, final UnarySBranchOp unaryStrategy) {
      return new SBranchInstruction(feasibilityChecker, new UnarySBranchStrategy(unaryStrategy));
   }

   public static Vop icmpgeInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpGeStrategy());
   }

   public static Vop icmpleInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpLeStrategy());
   }

   public static Vop icmpneInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpNeStrategy());
   }

   public static Vop icmpeqInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpEqStrategy());
   }

   public static Vop icmpgtInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpGtStrategy());
   }

   public static Vop icmpltInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpLtStrategy());
   }

   private static Vop icmpInstruction(final FeasibilityChecker feasibilityChecker, final BinarySBranchOp icmpStrategy) {
      final BinarySBranchStrategy binaryBranchStrategy = new BinarySBranchStrategy(icmpStrategy);
      return new SBranchInstruction(feasibilityChecker, binaryBranchStrategy);
   }
}