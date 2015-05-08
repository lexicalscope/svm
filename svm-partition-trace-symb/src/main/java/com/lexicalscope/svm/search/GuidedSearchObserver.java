package com.lexicalscope.svm.search;

import com.lexicalscope.svm.vm.j.JState;

public interface GuidedSearchObserver {
   void picked(JState pending, GuidedSearchState side);

   void goal(JState pending);
}
