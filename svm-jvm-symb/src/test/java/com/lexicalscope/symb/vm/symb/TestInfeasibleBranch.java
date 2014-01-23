package com.lexicalscope.symb.vm.symb;

import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import com.lexicalscope.svm.j.instruction.symbolic.SymbInstructionFactory;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.symb.SymbVmFactory;

public class TestInfeasibleBranch {
   private final MethodInfo infeasibleMethod = new MethodInfo(StaticInfeasibleBranchMethod.class, "infeasible", "(I)I");

   public static class StaticInfeasibleBranchMethod {
      public static int infeasible(final int x) {
         final int y = x + 1;
         if(y - x < 0) {
            return 10;
         }
         return -10;
       }
   }

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
