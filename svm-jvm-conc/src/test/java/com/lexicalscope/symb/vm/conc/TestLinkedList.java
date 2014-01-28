package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.j.State;

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
