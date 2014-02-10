package com.lexicalscope.svm.j.instruction.symbolic.ops.object;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.OTerminalSymbol;
import com.lexicalscope.svm.stack.trace.SMethodName;
import com.lexicalscope.svm.vm.j.MethodResolver;
import com.lexicalscope.svm.vm.j.klass.SMethod;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

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
}
