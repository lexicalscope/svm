package com.lexicalscope.symb.vm.symbinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIAddOperator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIConstM1Operator;
import com.lexicalscope.symb.vm.symbinstructions.ops.SIMulOperator;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ValueSymbol;

public class SymbInstructionFactory implements InstructionFactory {
	private int symbol = -1;

	@Override
	public BinaryOperator iaddOperation() {
		return new SIAddOperator();
	}

	@Override
	public BinaryOperator imulOperation() {
	   return new SIMulOperator();
	}

	public Symbol symbol() {
		return new ValueSymbol(++symbol);
	}

	@Override
	public Instruction branchIfge(final JumpInsnNode jumpInsnNode) {
		return new Instruction() {
         @Override
         public void eval(final SClassLoader cl, final Vm vm, final State state) {
            final State[] states = state.fork();

            // TODO[tim]: update PC and check feasibility here

            // left
            states[0].op(new StackFrameVop() {
               @Override
               public void eval(final StackFrame stackFrame) {
                  stackFrame.pop(); // discard, should check feasibility
                  stackFrame.advance(cl.instructionFor(jumpInsnNode.label.getNext()));
               }
            });

            // right
            states[1].op(new StackFrameVop() {
               @Override
               public void eval(final StackFrame stackFrame) {
                  stackFrame.pop(); // discard, should check feasibility
                  stackFrame.advance(cl.instructionFor(jumpInsnNode.getNext()));
               }
            });

            vm.fork(states);
         }

         @Override
         public String toString() {
            return "IFGE";
         }
      };
	}

	@Override
	public NullaryOperator iconst_m1() {
		return new SIConstM1Operator();
	}
}
