package com.lexicalscope.symb.vm.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructions;
import com.lexicalscope.symb.vm.concinstructions.Instructions;

public class SClassLoader {
	private final Instructions instructions;

	public SClassLoader(final Instructions instructions) {
		this.instructions = instructions;
	}

	public SClassLoader() {
		this(new ConcInstructions());
	}

	public SClass load(final String name) {
		try {
			final ClassNode classNode = new ClassNode();

			final InputStream in = this
					.getClass()
					.getClassLoader()
					.getResourceAsStream(
							name.replace(".", File.separator) + ".class");

			if (in == null) {
				throw new SClassNotFoundException(name);
			}

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

	public Instruction instructionFor(AbstractInsnNode abstractInsnNode) {
		return instructions.instructionFor(abstractInsnNode);
	}
}
