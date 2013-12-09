package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface Instructions {
	Instruction instructionFor(SClassLoader classLoader, AbstractInsnNode abstractInsnNode, Instruction previous);
}