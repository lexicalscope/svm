package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.conc.checkingheap.ConcreteHeapCheck;
import com.lexicalscope.symb.vm.conc.checkingheap.HeapCheck;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public class SymbolicHeapCheck implements HeapCheck {
   private final ConcreteHeapCheck concreteCheck = new ConcreteHeapCheck();

   @Override public boolean allowedInIntField(final Object val) {
      return concreteCheck.allowedInIntField(val) || val instanceof ISymbol;
   }
}
