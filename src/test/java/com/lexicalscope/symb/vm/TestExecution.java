package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.loadConstants;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiation;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TestExecution {
   private final Vm vm = new Vm();
   
   @Test
   public void executeEmptyMainMethod() {
      final State initial = vm.initial("com/lexicalscope/symb/vm/EmptyStaticMethod", "main", "()V");

      assertThat(vm.execute(initial), normalTerminiation());
   }

   @Test
   public void executeStaticAddMethod() {
      final State initial = vm.initial("com/lexicalscope/symb/vm/StaticAddMethod", "add", "(II)I").op(loadConstants(1,2));
      final State result = vm.execute(initial);
      
      assertThat(result, normalTerminiationWithResult(3));
   }
}
