package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.LabelNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public class Label implements Instruction {
	private final LabelNode abstractInsnNode;

	public Label(final LabelNode abstractInsnNode) {
		this.abstractInsnNode = abstractInsnNode;
	}

	@Override
	public void eval(final SClassLoader cl, final State state) {
		new NextInstruction().next(cl, state, abstractInsnNode);
	}

	@Override
	public String toString() {
		return "LABEL";
	}
}
