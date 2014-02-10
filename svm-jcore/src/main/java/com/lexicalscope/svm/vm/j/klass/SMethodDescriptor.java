package com.lexicalscope.svm.vm.j.klass;

import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.SVirtualMethodName;

public interface SMethodDescriptor extends SMethodName {
   boolean isVoidMethod();

   String klassName();

   String name();

   String desc();

   int argSize();
   int returnCount();

   SVirtualMethodName virtualName();
}