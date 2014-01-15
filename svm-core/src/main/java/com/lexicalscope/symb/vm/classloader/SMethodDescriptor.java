package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.SMethodName;

public interface SMethodDescriptor extends SMethodName {

   boolean isVoidMethod();

   String klassName();

   String name();

   String desc();

   int argSize();

   SVirtualMethodName virtualName();
}