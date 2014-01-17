package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.state.SMethodName;

public interface SMethodDescriptor extends SMethodName {

   boolean isVoidMethod();

   String klassName();

   String name();

   String desc();

   int argSize();

   SVirtualMethodName virtualName();
}