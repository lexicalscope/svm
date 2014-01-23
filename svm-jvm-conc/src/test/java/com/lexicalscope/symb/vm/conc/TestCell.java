package com.lexicalscope.symb.vm.conc;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static com.lexicalscope.symb.vm.conc.VmFactory.concreteVm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;

public class TestCell {
   public static class Cell {
      private int val;

      public static int viaCell(final int x) {
         final Cell cell = new Cell();
         cell.set(x);
         return cell.get();
      }

      public int get() {
         return val;
      }

      public void set(final int val) {
         this.val = val;
      }
   }

   private final MethodInfo viaCellMethod = new MethodInfo(Cell.class, "viaCell", "(I)I");

   @Test
   public void concExecuteCellNewGetSet() {
      final Vm<State> vm = concreteVm(viaCellMethod, -6);
      assertThat(vm.execute(), normalTerminiationWithResult(-6));
   }
}
