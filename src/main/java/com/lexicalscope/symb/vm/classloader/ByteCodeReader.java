package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.classloader.asm.AsmSClass;

public interface ByteCodeReader {
   AsmSClass load(SClassLoader classLoader, String name, ClassLoaded classLoaded);
}