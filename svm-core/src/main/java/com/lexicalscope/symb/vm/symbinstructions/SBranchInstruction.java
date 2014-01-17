package com.lexicalscope.symb.vm.symbinstructions;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.symbinstructions.predicates.BinarySBranchOp;
import com.lexicalscope.symb.vm.symbinstructions.predicates.BinarySBranchStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.EqStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.GeStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.GtStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.ICmpEqStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.ICmpGeStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.ICmpGtStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.ICmpLeStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.ICmpLtStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.ICmpNeStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.LeStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.LtStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.NeStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.SBranchStrategy;
import com.lexicalscope.symb.vm.symbinstructions.predicates.UnarySBranchOp;
import com.lexicalscope.symb.vm.symbinstructions.predicates.UnarySBranchStrategy;
import com.lexicalscope.symb.vm.symbinstructions.symbols.BoolSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.NotSymbol;
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

   @Override public void eval(final Context ctx) {
      final Pc pc = (Pc) ctx.getMeta();

      final BoolSymbol jumpSymbol = branchStrategy.branchPredicateSymbol(ctx.state());

      final Pc jumpPc = pc.snapshot().and(jumpSymbol);
      final boolean jumpFeasible = feasibilityChecker.check(jumpPc);

      final NotSymbol nojumpSymbol = new NotSymbol(jumpSymbol);
      final Pc nojumpPc = pc.snapshot().and(nojumpSymbol);
      final boolean nojumpFeasible = feasibilityChecker.check(nojumpPc);

      if(jumpFeasible && nojumpFeasible)
      {
         final State[] states = ctx.state().fork();

         // jump
         ((Pc) states[0].getMeta()).and(jumpSymbol);
         states[0].stackFrame().advance(ctx.instructionJmpTarget());

         // no jump
         ((Pc) states[1].getMeta()).and(nojumpSymbol);
         states[1].stackFrame().advance(ctx.instructionNext());

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