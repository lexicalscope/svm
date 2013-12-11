package com.lexicalscope.symb.vm;

import java.util.List;

import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SMethod;


public interface Statics extends Snapshotable<Statics> {
   SClass load(String klassName);
   SMethod loadMethod(String owner, String name, String desc);

   List<SClass> defineClass(String klass);
   void staticsAt(SClass klass, Object staticsAddress);
   Object whereMyStaticsAt(SClass klass);

   boolean isDefined(String klass);
}
