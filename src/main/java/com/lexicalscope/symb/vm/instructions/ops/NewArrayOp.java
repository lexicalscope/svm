package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;

public class NewArrayOp implements Vop {
   public static final int ARRAY_PREAMBLE = 1;

   // TODO - arrays can have different types
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final Object top = stackFrame.pop();
      // need to deal with symbolic lengths
      final int arrayLength;
      if(top instanceof IConstSymbol) {
         arrayLength = ((IConstSymbol) top).val();
      } else {
         arrayLength = (int) top;
      }

      final Object arrayAddress = heap.newObject(new Allocatable() {
         @Override public int fieldCount() {
            return arrayLength + ARRAY_PREAMBLE;
         }
      });
      heap.put(arrayAddress, 0, arrayLength);
      stackFrame.push(arrayAddress);
   }

   @Override public String toString() {
      return "NEWARRAY";
   }
}
