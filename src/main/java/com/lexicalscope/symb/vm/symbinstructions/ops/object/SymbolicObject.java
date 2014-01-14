package com.lexicalscope.symb.vm.symbinstructions.ops.object;

import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.classloader.VirtualMethodResolver;
import com.lexicalscope.symb.vm.symbinstructions.symbols.OTerminalSymbol;

public class SymbolicObject implements VirtualMethodResolver {
   private final OTerminalSymbol symbol;

   public SymbolicObject(final OTerminalSymbol symbol) {
      this.symbol = symbol;
   }

   @Override public String toString() {
      return "symbolic object " + symbol;
   }

   @Override public SMethodName resolve(final SMethodName sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public String name() {
      return symbol.klass();
   }
}
