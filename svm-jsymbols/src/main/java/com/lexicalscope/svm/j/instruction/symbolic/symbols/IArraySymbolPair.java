package com.lexicalscope.svm.j.instruction.symbolic.symbols;

public class IArraySymbolPair {
   private final IArraySymbol arraySymbol;
   private final ISymbol lengthSymbol;

   public IArraySymbolPair(final IArraySymbol arraySymbol, final ISymbol lengthSymbol) {
      this.arraySymbol = arraySymbol;
      this.lengthSymbol = lengthSymbol;
   }

   public IArraySymbol getArraySymbol() {
      return arraySymbol;
   }

   public ISymbol getLengthSymbol() {
      return lengthSymbol;
   }
}

