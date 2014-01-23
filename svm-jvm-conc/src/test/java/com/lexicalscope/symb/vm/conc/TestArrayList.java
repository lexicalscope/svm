package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.LinkedList;

import org.junit.Test;

import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.j.State;

public class TestArrayList {
   private final MethodInfo arrayListAddRemove = new MethodInfo(StaticArrayList.class, "addRemove", "(I)I");

   public static class StaticArrayList {
      public static int addRemove(final int x) {
         final LinkedList<Integer> list = new LinkedList<>();
         list.add(x);
         return list.removeFirst();
      }
   }

   @Test public void linkedListAddThenGet() {
      final Vm<State> vm = concreteVm(arrayListAddRemove, 4);
      assertThat(vm.execute(), normalTerminiationWithResult(4));
   }
}
