package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.AsmSMethodName;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public abstract class AbstractNativeMethodDef implements NativeMethodDef {
   private final SMethodName name;

   public AbstractNativeMethodDef(final String klass, final String name, final String desc) {
      this.name = new AsmSMethodName(klass, name, desc);
   }

   @Override public final SMethodName name() {
      return name;
   }
}
