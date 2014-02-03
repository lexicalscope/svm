package com.lexicalscope.symb.vm.symb;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.IAddSymbol;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.symb.junit.Fresh;
import com.lexicalscope.symb.vm.symb.junit.SymbVmRule;

public class TestAdd {
   @Rule public final SymbVmRule vm = new SymbVmRule();

   private @Fresh ISymbol symbol1;
   private @Fresh ISymbol symbol2;

   @TestEntryPoint public static int add(final int x, final int y) {
      return x + y;
   }

   @Test
   public void concExecuteStaticAddMethod() {
      assertThat(vm.execute(1, 2), normalTerminiationWithResult(3));
   }

   @Test
   public void symbExecuteStaticAddMethod() {
      assertThat(vm.execute(symbol1, symbol2), normalTerminiationWithResult(new IAddSymbol(symbol1, symbol2)));
   }
}
