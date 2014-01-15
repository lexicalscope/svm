package com.lexicalscope.symb.vm.symbinstructions.ops;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.symbinstructions.ops.object.SymbolicObject;
import com.lexicalscope.symb.vm.symbinstructions.symbols.OTerminalSymbol;

public class LoadSymbolicObjectArg implements Vop {
   private final OTerminalSymbol symbol;

   public LoadSymbolicObjectArg(final OTerminalSymbol symbol) {
      this.symbol = symbol;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object newObject = heap.newObject(new Allocatable() {
         @Override public int allocateSize() {
            return 2;
         }
      });
      heap.put(newObject, SClass.OBJECT_MARKER_OFFSET, new SymbolicObject(symbol));
      stackFrame.push(newObject);
   }
}
