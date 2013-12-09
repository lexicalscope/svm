package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.LineNumberNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.NextInstruction;

public class LineNumber implements InstructionTransform {
	private final LineNumberNode abstractInsnNode;

	public LineNumber(final LineNumberNode abstractInsnNode) {
		this.abstractInsnNode = abstractInsnNode;
	}

	@Override
	public void eval(final SClassLoader cl, final Vm vm, final State state, final Instruction instruction) {
		new NextInstruction().next(state, instruction);
	}

	@Override
	public String toString() {
		return "LINENUMBER";
	}
}
