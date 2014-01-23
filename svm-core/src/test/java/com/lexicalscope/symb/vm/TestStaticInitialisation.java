package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.conc.MethodInfo;

public class TestStaticInitialisation {
   MethodInfo returnStaticFieldValue = new MethodInfo(StaticField.class, "getX", "()I");

   @Test public void getStaticFieldViaStaticMethod() {
      final Vm<State> vm = concreteVm(returnStaticFieldValue);
      assertThat(vm.execute(), normalTerminiationWithResult(5));
   }
}
