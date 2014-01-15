package com.lexicalscope.symb.vm.classloader;

public interface MethodResolver {
   SMethod virtualMethod(SMethodName sMethodName);
   SMethod declaredMethod(SMethodName sMethodName);
   String name();
}