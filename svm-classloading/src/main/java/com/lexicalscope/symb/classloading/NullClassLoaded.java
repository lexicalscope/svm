package com.lexicalscope.symb.classloading;

import com.lexicalscope.symb.klass.SClass;

class NullClassLoaded implements ClassLoaded {
   @Override public void loaded(final SClass klass) { }
}
