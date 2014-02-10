package com.lexicalscope.svm.vm.conc;

import com.lexicalscope.svm.vm.Vm;
import com.lexicalscope.svm.vm.j.State;

public class VmFactory {
   public static Vm<State> concreteVm(final MethodInfo entryPoint, final Object ... args) {
      return new JvmBuilder().build(entryPoint, args);
   }
}
