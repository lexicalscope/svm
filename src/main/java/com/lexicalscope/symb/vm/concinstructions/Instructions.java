package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;

public interface Instructions {
	Instruction instructionFor(AbstractInsnNode abstractInsnNode);
}