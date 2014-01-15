package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.Vm.concreteVm;
import static com.lexicalscope.symb.vm.matchers.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.classloader.MethodInfo;

public class TestLinkedList {
   final MethodInfo linkedListAddRemove = new MethodInfo(StaticLinkedList.class, "addRemove", "(I)I");

   @Test public void linkedListAddThenGet() {
      final Vm vm = concreteVm(linkedListAddRemove, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(4));
   }
}