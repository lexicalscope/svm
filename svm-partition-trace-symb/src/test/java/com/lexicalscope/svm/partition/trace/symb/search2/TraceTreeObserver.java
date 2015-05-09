package com.lexicalscope.svm.partition.trace.symb.search2;

import com.lexicalscope.svm.vm.j.JState;

public interface TraceTreeObserver {
   void stateAdded(TraceTree traceTree, JState state);
}
