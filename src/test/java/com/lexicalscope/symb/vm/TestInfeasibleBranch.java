package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.Vm.concreteVm;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Ignore;
import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;
import com.lexicalscope.symb.vm.symbinstructions.symbols.Symbol;

public class TestInfeasibleBranch {
   MethodInfo infeasibleMethod = new MethodInfo(
         "com/lexicalscope/symb/vm/StaticInfeasibleBranchMethod", "infeasible", "(I)I");

   @Test
   public void concExecuteLeftBranch() {
      final Vm vm = concreteVm(infeasibleMethod, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(-10));
   }

   @Test @Ignore
   public void symbExecuteShouldSearchOnlyOneBranch() {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final Symbol symbol1 = instructionFactory.symbol();

      final Vm vm = Vm.vm(instructionFactory, infeasibleMethod, symbol1);
      vm.execute();
      assertThat(vm.results(), hasItem(normalTerminiationWithResult(new IConstSymbol(-10))));
      assertThat(vm.results(), hasSize(1));
   }
}
