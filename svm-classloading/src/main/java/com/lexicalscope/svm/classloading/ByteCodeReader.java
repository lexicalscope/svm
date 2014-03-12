package com.lexicalscope.svm.classloading;

import com.lexicalscope.svm.vm.j.klass.SClass;

public interface ByteCodeReader {
   SClass load(SClassLoader classLoader, String name);
}