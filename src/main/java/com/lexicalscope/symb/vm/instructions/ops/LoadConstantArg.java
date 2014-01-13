package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class LoadConstantArg implements Vop {
   private final Object value;

   public LoadConstantArg(final Object value) {
      assert value instanceof Integer || value instanceof ISymbol : value.getClass();
      this.value = value;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      stackFrame.loadConst(value);
   }
}