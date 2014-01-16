package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.symbinstructions.symbols.ISymbol;

public final class LoadConstantArg implements Vop {
   private final Object value;

   public LoadConstantArg(final Object value) {
      assert value instanceof Integer || value instanceof ISymbol : value.getClass();
      this.value = value;
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      stackFrame.push(value);
   }
}