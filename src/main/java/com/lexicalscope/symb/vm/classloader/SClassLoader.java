package com.lexicalscope.symb.vm.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.heap.FastHeap;
import com.lexicalscope.symb.vm.DequeStack;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;
import com.lexicalscope.symb.vm.instructions.Instructions;
import com.lexicalscope.symb.vm.instructions.InvokeStatic;

public class SClassLoader {
	private final Instructions instructions;
   private final InstructionFactory instructionFactory;

	public SClassLoader(final InstructionFactory instructionFactory) {
		this.instructionFactory = instructionFactory;
      this.instructions = new BaseInstructions(instructionFactory);
	}

	public SClassLoader() {
		this(new ConcInstructionFactory());
	}

	public SClass load(final String name) {
		try {
			final ClassNode classNode = new ClassNode();

			final InputStream in = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							name.replace(".", File.separator) + ".class");

			if (in == null)
				throw new SClassNotFoundException(name);

			try {
				new ClassReader(in).accept(classNode, 0);
			} finally {
				in.close();
			}
			return new SClass(instructions, classNode);
		} catch (final IOException e) {
			throw new SClassLoadingFailException(name, e);
		}
	}

	public SMethod loadMethod(final String klass, final String name,
			final String desc) {
		return load(klass).staticMethod(name, desc);
	}

	public Instruction instructionFor(final AbstractInsnNode abstractInsnNode) {
		return instructions.instructionFor(abstractInsnNode);
	}

	public State initial(final String klass) {
		return initial(klass, "main", "([Ljava/lang/String;)V");
	}

	public State initial(final MethodInfo info) {
		return initial(info.klass(), info.name(), info.desc());
	}

	public State initial(final String klass, final String name,
			final String desc) {
		final SMethod method = loadMethod(klass, name, desc);
		return new State(new DequeStack(new InvokeStatic(klass, name, desc), 0,
				method.argSize()), new FastHeap(), instructionFactory.initialMeta());
	}
}
