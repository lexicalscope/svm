package com.lexicalscope.symb.vm.natives;

import com.lexicalscope.symb.vm.classloader.AsmSMethodName;

public abstract class AbstractNativeMethodDef implements NativeMethodDef {
   private final AsmSMethodName name;

   public AbstractNativeMethodDef(final String klass, final String name, final String desc) {
      this.name = new AsmSMethodName(klass, name, desc);
   }

   @Override public final AsmSMethodName name() {
      return name;
   }
}
