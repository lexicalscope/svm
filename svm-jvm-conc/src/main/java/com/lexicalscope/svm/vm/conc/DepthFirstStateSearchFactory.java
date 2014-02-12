package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.search.DepthFirstStateSearch;

public class DepthFirstStateSearchFactory implements StateSearchFactory {
   @Override public StateSearch<State> search() {
      return new DepthFirstStateSearch<>();
   }
}
