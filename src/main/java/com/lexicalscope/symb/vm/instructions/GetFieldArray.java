package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.AsmSClass;
import com.lexicalscope.symb.vm.classloader.SClass;

public class GetFieldArray implements Vop {
   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final boolean publicOnly = 1 == (int) stackFrame.pop();
      final Object classAddress = stackFrame.pop();

      final SClass klassPointer = (SClass) heap.get(classAddress, statics.classClass().fieldIndex(AsmSClass.internalClassPointer));

   }
}
