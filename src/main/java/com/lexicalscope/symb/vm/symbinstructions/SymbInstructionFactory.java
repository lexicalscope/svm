package com.lexicalscope.symb.vm.symbinstructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIConstOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIMulOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SISubOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.GeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.NotSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.z3.FeasbilityChecker;

/**
 * @author tim
 */
public class SymbInstructionFactory implements InstructionFactory {
	private final class SBranchGEInstruction implements InstructionTransform {
      private final JumpInsnNode jumpInsnNode;

      private SBranchGEInstruction(final JumpInsnNode jumpInsnNode) {
         this.jumpInsnNode = jumpInsnNode;
      }

      @Override
      public void eval(final SClassLoader cl, final Vm vm, final State state, final InstructionNode instruction) {
         final Pc pc = (Pc) state.getMeta();
         final Symbol operand = (Symbol) state.op(popOperand());

         final GeSymbol jumpSymbol = new GeSymbol(operand);
         final Pc jumpPc = pc.snapshot().and(jumpSymbol);
         final boolean jumpFeasible = feasbilityChecker.check(jumpPc);

         final NotSymbol nojumpSymbol = new NotSymbol(jumpSymbol);
         final Pc nojumpPc = pc.snapshot().and(nojumpSymbol);
         final boolean nojumpFeasible = feasbilityChecker.check(nojumpPc);

         final StackFrameVop jumpOp = new StackFrameVop() {
            @Override
            public void eval(final StackFrame stackFrame) {
               stackFrame.advance(instruction.jmpTarget());
            }
         };

         final StackFrameVop nojumpOp = new StackFrameVop() {
            @Override
            public void eval(final StackFrame stackFrame) {
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
         return "IFGE";
      }
   }

	private final FeasbilityChecker feasbilityChecker = new FeasbilityChecker();
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

	public Symbol symbol() {
		return new ISymbol(++symbol);
	}

	@Override
	public InstructionTransform branchIfge(final JumpInsnNode jumpInsnNode) {
		return new SBranchGEInstruction(jumpInsnNode);
	}

	@Override
	public NullaryOperator iconst(final int val) {
		return new SIConstOperator(val);
	}

   @Override
   public Snapshotable<?> initialMeta() {
      return new Pc();
   }
}
