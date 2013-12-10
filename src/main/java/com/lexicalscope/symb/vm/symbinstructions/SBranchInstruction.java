package com.lexicalscope.symb.vm.symbinstructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.symbinstructions.symbols.GeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.NotSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasibilityChecker;

final class SBranchInstruction implements Instruction {
   public interface SBranchStrategy {
      GeSymbol conditionSymbol(Symbol operand);
   }

   private static final class GeStrategy implements SBranchStrategy {
      @Override public GeSymbol conditionSymbol(final Symbol operand) {
         return new GeSymbol(operand);
      }

      @Override public String toString() {
         return "IFGE";
      }
   }

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
      final Symbol operand = (Symbol) state.op(popOperand());

      final GeSymbol jumpSymbol = branchStrategy.conditionSymbol(operand);
      final Pc jumpPc = pc.snapshot().and(jumpSymbol);
      final boolean jumpFeasible = feasibilityChecker.check(jumpPc);

      final NotSymbol nojumpSymbol = new NotSymbol(jumpSymbol);
      final Pc nojumpPc = pc.snapshot().and(nojumpSymbol);
      final boolean nojumpFeasible = feasibilityChecker.check(nojumpPc);

      final Vop jumpOp = new Vop() {
         @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
            stackFrame.advance(instruction.jmpTarget());
         }
      };

      final Vop nojumpOp = new Vop() {
         @Override public void eval(final StackFrame stackFrame, Stack stack, final Heap heap, Statics statics) {
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
      return new SBranchInstruction(feasibilityChecker, new GeStrategy());
   }
}