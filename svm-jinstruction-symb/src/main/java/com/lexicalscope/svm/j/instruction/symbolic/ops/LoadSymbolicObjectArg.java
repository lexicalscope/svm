package com.lexicalscope.svm.j.instruction.symbolic.ops;

import com.lexicalscope.svm.j.instruction.symbolic.ops.object.SymbolicObject;
import com.lexicalscope.svm.j.instruction.symbolic.symbols.OTerminalSymbol;
import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.klass.SClass;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class LoadSymbolicObjectArg implements Vop {
   private final OTerminalSymbol symbol;

   public LoadSymbolicObjectArg(final OTerminalSymbol symbol) {
      this.symbol = symbol;
   }

   @Override public void eval(final State ctx) {
      final Object newObject = ctx.newObject(new Allocatable() {
         @Override public int allocateSize() {
            return 2;
         }
      });
      ctx.put(newObject, SClass.OBJECT_MARKER_OFFSET, new SymbolicObject(symbol));
      ctx.push(newObject);
   }
}