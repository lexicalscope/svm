package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SMethod;


public interface Statics extends Snapshotable<Statics> {
   SClass load(String klassName);
   SMethod loadMethod(String owner, String name, String desc);

   void defineClass(String klass);
   boolean isDefined(String klass);
}
