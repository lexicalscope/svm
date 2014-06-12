package com.lexicalscope.svm.partition.spec;

public interface CallContext extends Invocation {
   Invocation previously(String klass, String method);
}
