package com.lexicalscope.svm.vm.symb;

import com.lexicalscope.svm.j.instruction.symbolic.FeasibleBranchSearch;
import com.lexicalscope.svm.vm.DepthFirstStateSearch;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class FeasibleBranchSearchFactory implements StateSearchFactory {
   private final FeasibilityChecker feasibilityChecker;

   public FeasibleBranchSearchFactory(final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override public StateSearch<State> search() {
      return new FeasibleBranchSearch(new DepthFirstStateSearch<State>(), feasibilityChecker);
   }
}
