package com.lexicalscope.symb.vm.symbinstructions.ops.array;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.instructions.ops.array.ArrayConstructor;
import com.lexicalscope.symb.vm.instructions.ops.array.InitStrategy;
import com.lexicalscope.symb.vm.instructions.ops.array.NewConcArray;
import com.lexicalscope.symb.vm.symbinstructions.symbols.IConstSymbol;

public class NewSymbArray implements ArrayConstructor {
   @Override public void newArray(final StackFrame stackFrame, final Heap heap, final Statics statics, final InitStrategy initStrategy) {
      final Object top = stackFrame.pop();
      // need to deal with symbolic lengths
      final int arrayLength;
      if(top instanceof IConstSymbol) {
         arrayLength = ((IConstSymbol) top).val();
      } else {
         arrayLength = (int) top;
      }
      new NewConcArray().newConcreteArray(stackFrame, heap, statics, arrayLength, initStrategy);
   }
}
