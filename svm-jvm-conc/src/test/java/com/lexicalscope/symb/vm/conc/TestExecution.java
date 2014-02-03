package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiation;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestExecution {
   @Rule public final VmRule vm = new VmRule();

   @TestEntryPoint public static void main() {
      return;
   }

   @Test public void executeEmptyMainMethod() {
      assertThat(vm.execute(), normalTerminiation());
   }
}
