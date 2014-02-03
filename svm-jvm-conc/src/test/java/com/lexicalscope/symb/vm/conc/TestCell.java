package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.j.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.symb.vm.conc.junit.TestEntryPoint;
import com.lexicalscope.symb.vm.conc.junit.VmRule;

public class TestCell {
   public static class Cell {
      private int val;

      public int get() {
         return val;
      }

      public void set(final int val) {
         this.val = val;
      }
   }

   @TestEntryPoint public static int viaCell(final int x) {
      final Cell cell = new Cell();
      cell.set(x);
      return cell.get();
   }

   @Rule public final VmRule vm = new VmRule();

   @Test
   public void concExecuteCellNewGetSet() {
      assertThat(vm.execute(-6), normalTerminiationWithResult(-6));
   }
}
