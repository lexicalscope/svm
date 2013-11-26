package com.lexicalscope.symb.vm;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructions;
import com.lexicalscope.symb.vm.concinstructions.Instructions;
import com.lexicalscope.symb.vm.concinstructions.InvokeStatic;
import com.lexicalscope.symb.vm.concinstructions.TerminationException;

public class Vm {
	private final SClassLoader classLoader;

	public Vm(Instructions instructions) {
		classLoader = new SClassLoader(instructions);
	}
	
	public Vm() {
		this(new ConcInstructions());
	}
	
	public State execute(final String klass) {
		return execute(initial(klass));
	}

	public State execute(final State state) {
		try {
			while (true) {
				System.out.println(state);
				state.advance(classLoader);
			}
		} catch (final TerminationException termination) {
			return termination.getFinalState();
		}
	}

	public SClass loadClass(final String name) {
		return classLoader.load(name);
	}

	public State initial(final String klass) {
		return initial(klass, "main", "([Ljava/lang/String;)V");
	}

	public State initial(final String klass, final String name,
			final String desc) {
		final SMethod method = classLoader.loadMethod(klass, name, desc);
		return new State(new Stack(new InvokeStatic(klass, name, desc), 0,
				method.argSize()), new Heap());
	}

	

	public Instruction instructionFor(AbstractInsnNode abstractInsnNode) {
		return new ConcInstructions().instructionFor(abstractInsnNode);
	}
}
