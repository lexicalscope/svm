package com.lexicalscope.symb.vm;

public interface SVirtualMethodName extends Comparable<SVirtualMethodName> {
   boolean isVoidMethod();

   String name();

   String desc();
}