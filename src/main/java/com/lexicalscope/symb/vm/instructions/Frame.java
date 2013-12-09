package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.concinstructions.NextInstruction;

public class Frame implements Instruction {
	@Override
	public void eval(final SClassLoader cl, final Vm vm, final State state, final InstructionNode instruction) {
		new NextInstruction().next(state, instruction);
	}

	@Override
	public String toString() {
		return "FRAME";
	}
}
