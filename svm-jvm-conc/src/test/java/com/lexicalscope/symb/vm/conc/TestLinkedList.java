package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestLinkedList {
   @Rule public final VmRule vm = new VmRule();

   @TestEntryPoint public static int addRemove(final int x) {
      final ArrayList<Integer> list = new ArrayList<>();
      list.add(x);
      return list.remove(0);
   }

   @Test public void linkedListAddThenGet() {
      assertThat(vm.execute(4), normalTerminiationWithResult(4));
   }
}
