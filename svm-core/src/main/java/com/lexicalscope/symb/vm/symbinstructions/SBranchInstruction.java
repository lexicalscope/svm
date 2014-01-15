package com.lexicalscope.symb.vm.symbinstructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
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

final class SBranchInstruction implements Instruction {
   private final FeasibilityChecker feasibilityChecker;
   private final SBranchStrategy branchStrategy;

   SBranchInstruction(
         final FeasibilityChecker feasibilityChecker,
         final SBranchStrategy branchStrategy
         ) {
      this.feasibilityChecker = feasibilityChecker;
      this.branchStrategy = branchStrategy;
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      final Pc pc = (Pc) state.getMeta();

      final BoolSymbol jumpSymbol = branchStrategy.branchPredicateSymbol(state);

      final Pc jumpPc = pc.snapshot().and(jumpSymbol);
      final boolean jumpFeasible = feasibilityChecker.check(jumpPc);

      final NotSymbol nojumpSymbol = new NotSymbol(jumpSymbol);
      final Pc nojumpPc = pc.snapshot().and(nojumpSymbol);
      final boolean nojumpFeasible = feasibilityChecker.check(nojumpPc);

      final Vop jumpOp = new Vop() {
         @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
            stackFrame.advance(instruction.jmpTarget());
         }
      };

      final Vop nojumpOp = new Vop() {
         @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
            stackFrame.advance(instruction.next());
         }
      };

      if(jumpFeasible && nojumpFeasible)
      {
         final State[] states = state.fork();

         // jump
         ((Pc) states[0].getMeta()).and(jumpSymbol);
         states[0].op(jumpOp);

         // no jump
         ((Pc) states[1].getMeta()).and(nojumpSymbol);
         states[1].op(nojumpOp);

         vm.fork(states);
      } else if(jumpFeasible) {
         state.op(jumpOp);
      } else if(nojumpFeasible) {
         state.op(nojumpOp);
      } else {
         throw new RuntimeException("unable to check feasibility");
      }
   }

   @Override
   public String toString() {
      return branchStrategy.toString();
   }

   public static Instruction geInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new GeStrategy());
   }

   public static Instruction gtInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new GtStrategy());
   }

   public static Instruction leInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new LeStrategy());
   }

   public static Instruction ltInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new LtStrategy());
   }

   public static Instruction neInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new NeStrategy());
   }

   public static Instruction eqInstruction(final FeasibilityChecker feasibilityChecker) {
      return ibranchInstruction(feasibilityChecker, new EqStrategy());
   }

   private static Instruction ibranchInstruction(final FeasibilityChecker feasibilityChecker, final UnarySBranchOp unaryStrategy) {
      return new SBranchInstruction(feasibilityChecker, new UnarySBranchStrategy(unaryStrategy));
   }

   public static Instruction icmpgeInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpGeStrategy());
   }

   public static Instruction icmpleInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpLeStrategy());
   }

   public static Instruction icmpneInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpNeStrategy());
   }

   public static Instruction icmpeqInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpEqStrategy());
   }

   public static Instruction icmpgtInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpGtStrategy());
   }

   public static Instruction icmpltInstruction(final FeasibilityChecker feasibilityChecker) {
      return icmpInstruction(feasibilityChecker, new ICmpLtStrategy());
   }

   private static Instruction icmpInstruction(final FeasibilityChecker feasibilityChecker, final BinarySBranchOp icmpStrategy) {
      final BinarySBranchStrategy binaryBranchStrategy = new BinarySBranchStrategy(icmpStrategy);
      return new SBranchInstruction(feasibilityChecker, binaryBranchStrategy);
   }
}