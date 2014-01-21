package com.lexicalscope.symb.classloading;

import com.lexicalscope.symb.klass.SClass;

public interface ByteCodeReader {
   SClass load(SClassLoader classLoader, String name, ClassLoaded classLoaded);
}