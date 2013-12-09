package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface Instructions {
	InstructionNode instructionFor(SClassLoader classLoader, AbstractInsnNode abstractInsnNode, InstructionNode previous);
}