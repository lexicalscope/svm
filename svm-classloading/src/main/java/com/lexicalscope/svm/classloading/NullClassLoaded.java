package com.lexicalscope.svm.classloading;

import com.lexicalscope.svm.vm.j.klass.SClass;

class NullClassLoaded implements ClassLoaded {
   @Override public void loaded(final SClass klass) { }
}
