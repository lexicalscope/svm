package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.loadConstants;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.MultiplySymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class TestBranch {
	MethodInfo absMethod = new MethodInfo(
			"com/lexicalscope/symb/vm/StaticAbsMethod", "abs", "(I)I");
	
	@Test
	public void concExecuteLeftBranch() {
		final Vm vm = new Vm();
		final State initial = vm.initial(absMethod).op(
				loadConstants(-2));
		final State result = vm.execute(initial);

		assertThat(result, normalTerminiationWithResult(2));
	}
	
	@Test
	public void concExecuteRightBranch() {
		final Vm vm = new Vm();
		final State initial = vm.initial(absMethod).op(
				loadConstants(2));
		final State result = vm.execute(initial);

		assertThat(result, normalTerminiationWithResult(2));
	}

	@Test
	@Ignore
	public void symbExecuteStaticAbsMethod() {
		final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
		final Symbol symbol1 = instructionFactory.symbol();
		
		final Vm vm = new Vm(instructionFactory);
		final State initial = vm.initial(absMethod).op(
				loadConstants(symbol1));
		final State result = vm.execute(initial);

		assertThat(result, normalTerminiationWithResult(new MultiplySymbol(symbol1, new ConstSymbol(-1))));
	}
}
