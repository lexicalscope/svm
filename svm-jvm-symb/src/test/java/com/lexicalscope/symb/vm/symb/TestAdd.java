package com.lexicalscope.symb.vm.symb;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.IAddSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.symb.SymbVmFactory;

public class TestAdd {
   private final MethodInfo addMethod = new MethodInfo(StaticAddMethod.class, "add", "(II)I");

   public static class StaticAddMethod {
      public static int add(final int x, final int y) {
         return x + y;
      }
   }

   @Test
   public void concExecuteStaticAddMethod() {
      assertThat(concreteVm(addMethod, 1, 2).execute(), normalTerminiationWithResult(3));
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
