package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiation;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TestExecution {
   private final Vm vm = new Vm();
   
   @Test
   public void executeEmptyMainMethod() {
      final State initial = vm.initial("com/lexicalscope/symb/vm/EmptyStaticMethod", "main", "()V");

      assertThat(vm.execute(initial), normalTerminiation());
   }
}
