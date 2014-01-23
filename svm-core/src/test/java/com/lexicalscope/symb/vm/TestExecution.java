package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiation;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.conc.VmFactory;

public class TestExecution {
   private final MethodInfo entryPoint = new MethodInfo(EmptyStaticMethod.class, "main", "()V");

   @Test public void executeEmptyMainMethod() {
      final Vm<State> vm = VmFactory.concreteVm(entryPoint);

      assertThat(vm.execute(), normalTerminiation());
   }
}
