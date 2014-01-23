package com.lexicalscope.symb.vm.symbolic;

import static com.lexicalscope.symb.vm.StateMatchers.normalTerminiationWithResult;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.SymbVmFactory;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.conc.MethodInfo;
import com.lexicalscope.symb.vm.symbinstructions.SymbInstructionFactory;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

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
   public void symbExecuteCellNewGetSet() {
      final SymbInstructionFactory instructionFactory = new SymbInstructionFactory();
      final ISymbol symbol1 = instructionFactory.isymbol();

      final Vm<State> vm = SymbVmFactory.symbolicVm(instructionFactory, viaCellMethod, symbol1);
      assertThat(vm.execute(), normalTerminiationWithResult(symbol1));
   }
}
