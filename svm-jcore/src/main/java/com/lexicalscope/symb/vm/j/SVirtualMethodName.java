package com.lexicalscope.symb.vm.j;

public interface SVirtualMethodName extends Comparable<SVirtualMethodName> {
   boolean isVoidMethod();

   String name();

   String desc();
}