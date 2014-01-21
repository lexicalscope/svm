package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiation;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TestExecution {
   private final MethodInfo entryPoint = new MethodInfo("com/lexicalscope/symb/vm/EmptyStaticMethod", "main", "()V");

   @Test public void executeEmptyMainMethod() {
      final Vm<State> vm = VmFactory.concreteVm(entryPoint);

      assertThat(vm.execute(), normalTerminiation());
   }
}
