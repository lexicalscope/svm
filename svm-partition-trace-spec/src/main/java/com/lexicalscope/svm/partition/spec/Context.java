package com.lexicalscope.svm.partition.spec;

public interface Context {
   StackFrame previously(String receiverClass, String methodName);
}
