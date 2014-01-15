package com.lexicalscope.symb.vm.classloader;

public interface SVirtualMethodName extends Comparable<SVirtualMethodName> {
   boolean isVoidMethod();

   String name();

   String desc();
}