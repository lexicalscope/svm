package com.lexicalscope.symb.vm.classloader;

public interface ByteCodeReader {
   SClass load(SClassLoader classLoader, String name);
}