package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiation;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

public class TestExecution {
   @TestEntryPoint private final MethodInfo entryPoint = new MethodInfo(EmptyStaticMethod.class, "main", "()V");
   @Rule public final VmRule vm = new VmRule();

   @Test public void executeEmptyMainMethod() {
      assertThat(vm.execute(), normalTerminiation());
   }
}
