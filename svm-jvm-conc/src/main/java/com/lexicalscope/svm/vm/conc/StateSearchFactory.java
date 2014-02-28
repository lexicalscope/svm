package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;

public interface StateSearchFactory {
   StateSearch<JState> search();
}
