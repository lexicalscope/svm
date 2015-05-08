package com.lexicalscope.svm.vm.symb;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.search.DepthFirstStateSearch;

public class FeasibleBranchSearchFactory implements StateSearchFactory {
   @Override public StateSearch<JState> search() {
      return new DepthFirstStateSearch<JState>();
   }
}
