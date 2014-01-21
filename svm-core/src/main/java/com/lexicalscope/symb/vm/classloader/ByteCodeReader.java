package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.classloading.ClassLoaded;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.klass.SClass;

public interface ByteCodeReader {
   SClass load(SClassLoader classLoader, String name, ClassLoaded classLoaded);
}