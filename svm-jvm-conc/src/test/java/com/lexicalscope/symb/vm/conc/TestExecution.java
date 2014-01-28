package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiation;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.j.State;

public class TestExecution {
   private final MethodInfo entryPoint = new MethodInfo(EmptyStaticMethod.class, "main", "()V");

   @Test public void executeEmptyMainMethod() {
      final Vm<State> vm = VmFactory.concreteVm(entryPoint);

      assertThat(vm.execute(), normalTerminiation());
   }
}
