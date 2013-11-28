package com.lexicalscope.symb.vm.symbinstructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.popOperand;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.Snapshotable;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIConstOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIMulOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SISubOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.GeSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ValueSymbol;

public class SymbInstructionFactory implements InstructionFactory {
	private static final class SBranchGEInstruction implements Instruction {
      private final JumpInsnNode jumpInsnNode;

      private SBranchGEInstruction(final JumpInsnNode jumpInsnNode) {
         this.jumpInsnNode = jumpInsnNode;
      }

      @Override
      public void eval(final SClassLoader cl, final Vm vm, final State state) {
         final Pc pc = (Pc) state.getMeta();
         final Symbol operand = (Symbol) state.op(popOperand());

         pc.and(new GeSymbol(operand));

         final State[] states = state.fork();

         // TODO[tim]: update PC and check feasibility here

         // jump
         final Pc jump = (Pc) states[0].getMeta();
         states[0].op(new StackFrameVop() {
            @Override
            public void eval(final StackFrame stackFrame) {
               stackFrame.advance(cl.instructionFor(jumpInsnNode.label.getNext()));
            }
         });

         // no jump
         final Pc noJump = (Pc) states[0].getMeta();
         states[1].op(new StackFrameVop() {
            @Override
            public void eval(final StackFrame stackFrame) {
               stackFrame.advance(cl.instructionFor(jumpInsnNode.getNext()));
            }
         });

         vm.fork(states);
      }

      @Override
      public String toString() {
         return "IFGE";
      }
   }

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
		return new ValueSymbol(++symbol);
	}

	@Override
	public Instruction branchIfge(final JumpInsnNode jumpInsnNode) {
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
