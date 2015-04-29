package com.lexicalscope.svm.vm.j.klass;

import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.SVirtualMethodName;

public interface SMethodDescriptor extends SMethodName {
   boolean isVoidMethod();
   boolean isConstructor();

   String klassName();
   boolean declaredOn(String klassInternalName);

   String name();

   String desc();

   int[] objectArgIndexes();
   boolean returnIsObject();
   int argSize();
   int returnCount();

   SVirtualMethodName virtualName();
}