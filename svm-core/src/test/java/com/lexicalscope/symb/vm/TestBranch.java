package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static com.lexicalscope.symb.vm.conc.VmFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IConstSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.MulSymbol;
import com.lexicalscope.symb.vm.conc.MethodInfo;

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

      final Vm<State> vm = SymbVmFactory.symbolicVm(instructionFactory, absMethod, symbol1);
      vm.execute();
      assertThat(vm.results(), hasSize(2));
      assertThat(vm.results(), hasItem(normalTerminiationWithResult(new MulSymbol(symbol1, new IConstSymbol(-1)))));
      assertThat(vm.results(), hasItem(normalTerminiationWithResult(symbol1)));
   }
}
