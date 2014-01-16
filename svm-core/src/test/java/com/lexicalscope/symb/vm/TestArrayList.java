package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.VmFactory.concreteVm;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;

public class TestArrayList {
   final MethodInfo arrayListAddRemove = new MethodInfo(StaticArrayList.class, "addRemove", "(I)I");

   @Test public void linkedListAddThenGet() {
      final Vm vm = concreteVm(arrayListAddRemove, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(4));
   }
}
