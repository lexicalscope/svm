package com.lexicalscope.symb.vm.symbinstructions.symbols;

public class StateBuilder {
   private int arrayName = 0;
   private int intName = 0;

   public IArraySymbol iarraySymbol() {
      return new IArrayTerminalSymbol(arrayName++);
   }

   public IArraySymbol iarrayZeroed() {
      return new IArrayZeroedSymbol();
   }

   public ITerminalSymbol intSymbol() {
      return new ITerminalSymbol(intName++);
   }

   public IConstSymbol intConst(final int i) {
      return new IConstSymbol(i);
   }

   public IArraySymbol arrayStore(final IArraySymbol arraySymbol, final ISymbol indexSymbol, final ISymbol valueSymbol) {
      return new IArrayStoreSymbol(arraySymbol, indexSymbol, valueSymbol);
   }

   public IArraySelectSymbol arraySelect(final IArraySymbol arraySymbol, final ISymbol indexSymbol) {
      return new IArraySelectSymbol(arraySymbol, indexSymbol);
   }

   public IAddSymbol iadd(final ISymbol value1, final ISymbol value2) {
      return new IAddSymbol(value1, value2);
   }
}
