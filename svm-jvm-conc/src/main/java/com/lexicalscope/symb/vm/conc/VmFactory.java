package com.lexicalscope.symb.vm.conc;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.j.State;

public class VmFactory {
   public static Vm<State> concreteVm(final MethodInfo entryPoint, final Object ... args) {
      return new JvmBuilder().build(entryPoint, args);
   }
}
