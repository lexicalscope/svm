package com.lexicalscope.svm.vm.symb;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.ISymbol;
import com.lexicalscope.svm.vm.conc.checkingheap.ConcreteHeapCheck;
import com.lexicalscope.svm.vm.conc.checkingheap.HeapCheck;

public class SymbolicHeapCheck implements HeapCheck {
   private final ConcreteHeapCheck concreteCheck = new ConcreteHeapCheck();

   @Override public boolean allowedInIntField(final Object val) {
      return concreteCheck.allowedInIntField(val) || val instanceof ISymbol;
   }
}
