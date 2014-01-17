package com.lexicalscope.symb.vm.classloader;

import com.lexicalscope.symb.vm.SClass;

class NullClassLoaded implements ClassLoaded {
   @Override public void loaded(final SClass klass) { }
}
