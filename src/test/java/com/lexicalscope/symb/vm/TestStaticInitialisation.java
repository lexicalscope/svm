package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.Vm.concreteVm;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;

public class TestStaticInitialisation {
   MethodInfo returnStaticFieldValue = new MethodInfo(StaticField.class, "getX", "()I");

   @Test public void getStaticFieldViaStaticMethod() {
      final Vm vm = concreteVm(returnStaticFieldValue, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(-10));
   }
}
