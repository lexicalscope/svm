package com.lexicalscope.symb.vm.classloader;

public interface ByteCodeReader {
   AsmSClass load(SClassLoader classLoader, String name, ClassLoaded classLoaded);
}