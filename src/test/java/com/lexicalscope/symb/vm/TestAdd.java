package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.AddSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class TestAdd {
	MethodInfo addMethod = new MethodInfo(
			"com/lexicalscope/symb/vm/StaticAddMethod", "add", "(II)I");

	@Test
	public void concExecuteStaticAddMethod() {
		final Vm vm = Vm.concreteVm(addMethod, 1, 2);
		assertThat(vm.execute(), normalTerminiationWithResult(3));
	}

	@Test
	public void symbExecuteStaticAddMethod() {
		final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
		final Symbol symbol1 = instructionFactory.symbol();
		final Symbol symbol2 = instructionFactory.symbol();

		final Vm vm = Vm.vm(instructionFactory, addMethod,symbol1, symbol2);
		assertThat(vm.execute(), normalTerminiationWithResult(new AddSymbol(symbol1, symbol2)));
	}
}
