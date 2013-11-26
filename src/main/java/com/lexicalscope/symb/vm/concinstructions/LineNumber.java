package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.LineNumberNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class LineNumber implements Instruction {
	private final LineNumberNode abstractInsnNode;

	public LineNumber(final LineNumberNode abstractInsnNode) {
		this.abstractInsnNode = abstractInsnNode;
	}

	@Override
	public void eval(final SClassLoader cl, final State state) {
		new NextInstruction().next(cl, state, abstractInsnNode);
	}

	@Override
	public String toString() {
		return "LINENUMBER";
	}
}
