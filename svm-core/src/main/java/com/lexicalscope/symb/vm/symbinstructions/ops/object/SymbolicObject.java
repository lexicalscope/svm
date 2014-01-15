package com.lexicalscope.symb.vm.symbinstructions.ops.object;

import com.lexicalscope.symb.vm.classloader.MethodResolver;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.symbinstructions.symbols.OTerminalSymbol;

public class SymbolicObject implements MethodResolver {
   private final OTerminalSymbol symbol;

   public SymbolicObject(final OTerminalSymbol symbol) {
      this.symbol = symbol;
   }

   @Override public String toString() {
      return "symbolic object " + symbol;
   }

   @Override public SMethod virtualMethod(final SMethodName sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public SMethod declaredMethod(final SMethodName sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public String name() {
      return symbol.klass();
   }
}
