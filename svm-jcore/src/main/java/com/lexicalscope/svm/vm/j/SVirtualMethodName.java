package com.lexicalscope.svm.vm.j;

public interface SVirtualMethodName extends Comparable<SVirtualMethodName> {
   boolean isVoidMethod();

   String name();

   String desc();
}