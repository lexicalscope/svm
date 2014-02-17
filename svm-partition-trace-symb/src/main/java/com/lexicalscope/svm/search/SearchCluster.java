package com.lexicalscope.svm.search;

public interface SearchCluster<S> {
   boolean isEmpty();

   void add(S candidate);

   S candidate();
}