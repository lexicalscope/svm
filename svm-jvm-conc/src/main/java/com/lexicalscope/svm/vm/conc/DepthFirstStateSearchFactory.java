package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.search.DepthFirstStateSearch;

public class DepthFirstStateSearchFactory implements StateSearchFactory {
   @Override public StateSearch<JState> search() {
      return new DepthFirstStateSearch<>();
   }
}
