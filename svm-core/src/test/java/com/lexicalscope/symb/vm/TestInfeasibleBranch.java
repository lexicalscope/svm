package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.conc.VmFactory.*;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class TestInfeasibleBranch {
   MethodInfo infeasibleMethod = new MethodInfo(
         "com/lexicalscope/symb/vm/StaticInfeasibleBranchMethod", "infeasible", "(I)I");

   @Test
   public void concExecuteLeftBranch() {
      final Vm<State> vm = concreteVm(infeasibleMethod, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(-10));
   }

   @Test
   public void symbExecuteShouldSearchOnlyOneBranch() {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.isymbol();

      final Vm<State> vm = SymbVmFactory.symbolicVm(instructionFactory, infeasibleMethod, symbol1);
      vm.execute();
      assertThat(vm.results(), hasItem(normalTerminiationWithResult(-10)));
      assertThat(vm.results(), hasSize(1));
   }
}
