package com.lexicalscope.symb.vm.tests;

public class Cell {
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
