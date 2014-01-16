package com.lexicalscope.symb.vm.instructions.ops.array;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class NewArrayOp implements Vop {
   public static final int ARRAY_CLASS_OFFSET = 0;
   public static final int ARRAY_LENGTH_OFFSET = 1;
   public static final int ARRAY_PREAMBLE = 2;
   private final InitStrategy initStrategy;
   private final ArrayConstructor arrayConstructor;

   public NewArrayOp(final ArrayConstructor arrayConstructor) {
      this(new ReferenceInitStrategy(), arrayConstructor);
   }

   public NewArrayOp(final Object initialValue, final ArrayConstructor arrayConstructor) {
      this(new PrimitiveInitStrategy(initialValue), arrayConstructor);
   }

   private NewArrayOp(final InitStrategy initStrategy, final ArrayConstructor arrayConstructor) {
      this.initStrategy = initStrategy;
      this.arrayConstructor = arrayConstructor;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      arrayConstructor.newArray(stackFrame, heap, statics, initStrategy);
   }

   @Override public String toString() {
      return "NEWARRAY";
   }
}
