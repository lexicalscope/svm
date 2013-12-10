package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClass;

public interface Statics extends Snapshotable<Statics> {
   Object defineClass(SClass klass);
   boolean isDefined(SClass klass);
}
