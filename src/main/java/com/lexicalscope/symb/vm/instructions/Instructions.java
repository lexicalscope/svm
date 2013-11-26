package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;

public interface Instructions {
	Instruction instructionFor(AbstractInsnNode abstractInsnNode);
}