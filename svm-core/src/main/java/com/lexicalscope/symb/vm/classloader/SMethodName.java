package com.lexicalscope.symb.vm.classloader;

public interface SMethodName {

   boolean isVoidMethod();

   String klassName();

   String name();

   String desc();

   int argSize();

   SVirtualMethodName virtualName();
}