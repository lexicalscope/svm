package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.VmFactory.*;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.MulSymbol;

public class TestBranch {
   MethodInfo absMethod = new MethodInfo(
         "com/lexicalscope/symb/vm/StaticAbsMethod", "abs", "(I)I");

   @Test
   public void concExecuteLeftBranch() {
      final Vm<State> vm = concreteVm(absMethod, -2);
      assertThat(vm.execute(), normalTerminiationWithResult(2));
   }

   @Test
   public void concExecuteRightBranch() {
      final Vm<State> vm = concreteVm(absMethod, 2);
      assertThat(vm.execute(), normalTerminiationWithResult(2));
   }

   @Test
   public void symbExecuteBothBranches() {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.isymbol();

      final Vm<State> vm = vm(instructionFactory, absMethod, symbol1);
      vm.execute();
      assertThat(vm.results(), hasSize(2));
      assertThat(vm.results(), hasItem(normalTerminiationWithResult(new MulSymbol(symbol1, new IConstSymbol(-1)))));
      assertThat(vm.results(), hasItem(normalTerminiationWithResult(symbol1)));
   }
}
