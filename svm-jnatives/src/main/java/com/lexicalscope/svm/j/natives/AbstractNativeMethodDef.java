package com.lexicalscope.svm.j.natives;

import com.lexicalscope.svm.vm.j.code.AsmSMethodName;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public abstract class AbstractNativeMethodDef implements NativeMethodDef {
   private final SMethodDescriptor name;

   public AbstractNativeMethodDef(final String klass, final String name, final String desc) {
      this.name = new AsmSMethodName(klass, name, desc);
   }

   @Override public final SMethodDescriptor name() {
      return name;
   }
}
