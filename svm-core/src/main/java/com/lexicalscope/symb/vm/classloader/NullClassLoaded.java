package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.classloading.ClassLoaded;
import com.lexicalscope.symb.klass.SClass;

class NullClassLoaded implements ClassLoaded {
   @Override public void loaded(final SClass klass) { }
}
