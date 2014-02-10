package com.lexicalscope.svm.classloading;

import com.lexicalscope.svm.vm.j.klass.SClass;

public interface ClassLoaded {
   void loaded(SClass klass);
}
