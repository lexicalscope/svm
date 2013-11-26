package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.loadConstants;
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
		Vm vm = new Vm();
		final State initial = vm.initial(addMethod).op(
				loadConstants(1, 2));
		final State result = vm.execute(initial);

		assertThat(result, normalTerminiationWithResult(3));
	}

	@Test
	public void symbExecuteStaticAddMethod() {
		SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
		Symbol symbol1 = instructionFactory.symbol();
		Symbol symbol2 = instructionFactory.symbol();
		
		Vm vm = new Vm(instructionFactory);
		final State initial = vm.initial(addMethod).op(
				loadConstants(symbol1, symbol2));
		final State result = vm.execute(initial);

		assertThat(result, normalTerminiationWithResult(new AddSymbol(symbol1, symbol2)));
	}
}
