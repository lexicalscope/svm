package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.vm.DepthFirstStateSearch;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.State;

public class DepthFirstStateSearchFactory implements StateSearchFactory {
   @Override public StateSearch<State> search() {
      return new DepthFirstStateSearch<>();
   }
}
