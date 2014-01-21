package com.lexicalscope.symb.klass;

import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.vm.SVirtualMethodName;

public interface SMethodDescriptor extends SMethodName {

   boolean isVoidMethod();

   String klassName();

   String name();

   String desc();

   int argSize();

   SVirtualMethodName virtualName();
}