package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.JumpInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.ops.IAddOp;
import com.lexicalscope.symb.vm.concinstructions.ops.IConstM1Op;
import com.lexicalscope.symb.vm.concinstructions.ops.IMulOp;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.NullaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.BinaryOperator;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public class ConcInstructionFactory implements InstructionFactory {
	@Override public BinaryOperator iaddOperation() {
		return new IAddOp();
	}

	@Override
	public BinaryOperator imulOperation() {
		return new IMulOp();
	}

	@Override
	public Instruction branchIfge(final JumpInsnNode jumpInsnNode) {
		return new Instruction() {
			@Override
			public void eval(final SClassLoader cl, final State state) {
				state.op(new StackFrameVop() {
					@Override
					public void eval(final StackFrame stackFrame) {
						final int val = (int) stackFrame.pop();
						if(val >= 0) {
							stackFrame.advance(cl.instructionFor(jumpInsnNode.label.getNext()));
						} else {
							stackFrame.advance(cl.instructionFor(jumpInsnNode.getNext()));
						}
					}
				});
			}
			
			@Override
			public String toString() {
				return String.format("IFGE %s", jumpInsnNode.label);
			}
		};
	}

	@Override
	public NullaryOperator iconst_m1() {
		return new IConstM1Op();
	}
}
