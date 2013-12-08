package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SMethod {
	private final MethodNode method;
	private final Instructions instructions;

	public SMethod(
	      final Instructions instructions,
	      final MethodNode method) {
		this.instructions = instructions;
		this.method = method;
	}

	public int maxLocals() {
		return method.maxLocals;
	}

	public int maxStack() {
		return method.maxStack;
	}

	public Instruction entry() {
		return instructions.instructionFor(method.instructions.get(0));
	}

	public int argSize() {
		return Type.getMethodType(method.desc).getArgumentsAndReturnSizes() >> 2;
	}
}
