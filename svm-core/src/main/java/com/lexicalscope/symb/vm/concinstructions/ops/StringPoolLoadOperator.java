package com.lexicalscope.symb.vm.concinstructions.ops;

import static com.lexicalscope.symb.vm.JavaConstants.STRING_CLASS;
import static org.objectweb.asm.Type.getInternalName;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.ops.NewObjectOp;
import com.lexicalscope.symb.vm.instructions.ops.array.NewArrayOp;
import com.lexicalscope.symb.vm.instructions.ops.array.NewConcArray;

public final class StringPoolLoadOperator implements Vop {
   private final String val;

   public StringPoolLoadOperator(final String val) {
      this.val = val;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final SClass stringClass = statics.load(STRING_CLASS);

      // create new string
      new NewObjectOp(getInternalName(String.class)).eval(stackFrame, stack, heap, statics);
      final Object stringAddress = stackFrame.pop();

      // create char array
      final char[] chars = val.toCharArray();

      stackFrame.push(chars.length);
      new NewArrayOp(new NewConcArray()).eval(stackFrame, stack, heap, statics);
      final Object valueAddress = stackFrame.pop();

      for (int i = 0; i < chars.length; i++) {
         heap.put(valueAddress, NewArrayOp.ARRAY_PREAMBLE + i, chars[i]);
      }

      heap.put(stringAddress, stringClass.fieldIndex(new SFieldName(STRING_CLASS, "value")), valueAddress);
      stackFrame.push(stringAddress);
   }

   @Override
   public String toString() {
      return "LDC string: " + new String(val);
   }
}
