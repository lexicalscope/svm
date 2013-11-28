package com.lexicalscope.symb.vm.tests;

import static com.lexicalscope.symb.vm.Vm.concreteVm;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.classloader.MethodInfo;

public class TestCell {
   MethodInfo viaCellMethod = new MethodInfo(
         "com/lexicalscope/symb/vm/tests/Cell", "viaCell", "(I)I");

   @Test
   public void concExecuteCellNewGetSet() {
      final Vm vm = concreteVm(viaCellMethod, -2);
      assertThat(vm.execute(), normalTerminiationWithResult(-2));
   }
}
