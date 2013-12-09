package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.FrameNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionTransform;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.NextInstruction;

public class Frame implements InstructionTransform {
	private final FrameNode abstractInsnNode;

	public Frame(final FrameNode abstractInsnNode) {
		this.abstractInsnNode = abstractInsnNode;
	}

	@Override
	public void eval(final SClassLoader cl, final Vm vm, final State state, final Instruction instruction) {
		new NextInstruction().next(cl, state, abstractInsnNode, instruction);
	}

	@Override
	public String toString() {
		return "FRAME";
	}
}
