package com.lexicalscope.symb.vm.tests;

import static com.lexicalscope.symb.vm.Vm.concreteVm;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class TestCell {
   MethodInfo viaCellMethod = new MethodInfo(
         "com/lexicalscope/symb/vm/tests/Cell", "viaCell", "(I)I");

   @Test
   public void concExecuteCellNewGetSet() {
      final Vm vm = concreteVm(viaCellMethod, -6);
      assertThat(vm.execute(), normalTerminiationWithResult(-6));
   }

   @Test @Ignore
   public void symbExecuteCellNewGetSet() {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, viaCellMethod, symbol1);
      assertThat(vm.execute(), normalTerminiationWithResult(symbol1));
   }
}
