package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.concinstructions.InvokeStatic;
import com.lexicalscope.symb.vm.concinstructions.TerminationException;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;

public class Vm {
	private final SClassLoader classLoader;

	public Vm(final InstructionFactory instructionFactory) {
		classLoader = new SClassLoader(instructionFactory);
	}
	
	public Vm() {
		this(new ConcInstructionFactory());
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

	public State initial(final MethodInfo info) {
		return initial(info.klass(), info.name(), info.desc());
	}
	
	public State initial(final String klass, final String name,
			final String desc) {
		final SMethod method = classLoader.loadMethod(klass, name, desc);
		return new State(new Stack(new SMethod(null, null), new InvokeStatic(klass, name, desc), 0,
				method.argSize()), new Heap());
	}
}
