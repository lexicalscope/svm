package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IAddSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class TestAdd {
   MethodInfo addMethod = new MethodInfo(
         "com/lexicalscope/symb/vm/StaticAddMethod", "add", "(II)I");

   @Test
   public void concExecuteStaticAddMethod() {
      final Vm<State> vm = concreteVm(addMethod, 1, 2);
      assertThat(vm.execute(), normalTerminiationWithResult(3));
   }

   @Test
   public void symbExecuteStaticAddMethod() {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.isymbol();
      final ISymbol symbol2 = instructionFactory.isymbol();

      final Vm<State> vm = SymbVmFactory.symbolicVm(instructionFactory, addMethod, symbol1, symbol2);
      assertThat(vm.execute(), normalTerminiationWithResult(new IAddSymbol(symbol1, symbol2)));
   }
}
