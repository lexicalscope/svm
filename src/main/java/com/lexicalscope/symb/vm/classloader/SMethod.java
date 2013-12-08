package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SMethod {
   private final SClassLoader classLoader;
	private final MethodNode method;
	private final Instructions instructions;
	private Instruction entryPoint;

	public SMethod(
	      final SClassLoader classLoader,
	      final Instructions instructions,
	      final MethodNode method) {
		this.classLoader = classLoader;
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
		return instructions.instructionFor(classLoader, getEntryPoint());
	}

	private AbstractInsnNode getEntryPoint() {
      return method.instructions.get(0);
   }

   public int argSize() {
		return Type.getMethodType(method.desc).getArgumentsAndReturnSizes() >> 2;
	}
}
