package com.lexicalscope.svm.partition.spec;

public interface Invocation {
   Receiver receiver();
   Value parameter(String path);
}
