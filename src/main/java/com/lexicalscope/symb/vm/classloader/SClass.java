package com.lexicalscope.symb.vm.classloader;

import java.util.List;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.instructions.Instructions;

public class SClass {
	private final ClassNode classNode;
	private final Instructions instructions;

	public SClass(Instructions instructions, final ClassNode classNode) {
		this.instructions = instructions;
		this.classNode = classNode;
	}

	@SuppressWarnings("unchecked")
	private List<MethodNode> methods() {
		return classNode.methods;
	}

	public SMethod staticMethod(final String name, final String desc) {
		for (final MethodNode method : methods()) {
			if (method.name.equals(name) && method.desc.equals(desc)) {
				return new SMethod(instructions, method);
			}
		}
		throw new SMethodNotFoundException("main");
	}
}
