package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.State;

public interface StateSearchFactory {
   StateSearch<State> search();
}
