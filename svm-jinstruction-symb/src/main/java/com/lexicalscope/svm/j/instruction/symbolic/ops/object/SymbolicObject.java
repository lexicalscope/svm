package com.lexicalscope.svm.j.instruction.symbolic.ops.object;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.OTerminalSymbol;
import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.vm.j.MethodResolver;
import com.lexicalscope.symb.vm.j.j.klass.SMethod;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class SymbolicObject implements MethodResolver {
   private final OTerminalSymbol symbol;

   public SymbolicObject(final OTerminalSymbol symbol) {
      this.symbol = symbol;
   }

   @Override public String toString() {
      return "symbolic object " + symbol;
   }

   @Override public SMethod virtualMethod(final SMethodDescriptor sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public SMethod declaredMethod(final SMethodName sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public String name() {
      return symbol.klass();
   }
}
