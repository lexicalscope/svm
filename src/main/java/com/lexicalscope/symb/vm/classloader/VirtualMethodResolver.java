package com.lexicalscope.symb.vm.classloader;

public interface VirtualMethodResolver {
   SMethodName resolve(SMethodName sMethodName);
   String name();
}