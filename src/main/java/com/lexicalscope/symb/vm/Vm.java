package com.lexicalscope.symb.vm;

import java.util.ArrayDeque;
import java.util.Deque;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.ConcInstructionFactory;
import com.lexicalscope.symb.vm.concinstructions.TerminationException;
import com.lexicalscope.symb.vm.instructions.InstructionFactory;

public class Vm {
	private final SClassLoader classLoader;
	private final Deque<State> pending = new ArrayDeque<>();
	private final Deque<State> finished = new ArrayDeque<>();

	public Vm(final State state, final InstructionFactory instructionFactory) {
		pending.push(state);
		classLoader = new SClassLoader(instructionFactory);
	}
	
	public Vm(final State state) {
		this(state, new ConcInstructionFactory());
	}

	public State execute(final State state) {
		try {
			while (true) {
				System.out.println(state);
				pending.peek().advance(classLoader);
			}
		} catch (final TerminationException termination) {
			return termination.getFinalState();
		}
	}
}
