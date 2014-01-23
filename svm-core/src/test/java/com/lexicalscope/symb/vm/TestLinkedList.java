package com.lexicalscope.symb.vm;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

import org.junit.Test;

import com.lexicalscope.symb.vm.conc.MethodInfo;

public class TestLinkedList {
   private final MethodInfo linkedListAddRemove = new MethodInfo(StaticLinkedList.class, "addRemove", "(I)I");

   public static class StaticLinkedList {
      public static int addRemove(final int x) {
         final ArrayList<Integer> list = new ArrayList<>();
         list.add(x);
         return list.remove(0);
      }
   }

   @Test public void linkedListAddThenGet() {
      final Vm<State> vm = concreteVm(linkedListAddRemove, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(4));
   }
}
