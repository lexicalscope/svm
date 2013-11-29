package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.BaseInstructions.fieldKey;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.StackFrame;

final class PutFieldOp implements HeapVop {
   private final FieldInsnNode fieldInsnNode;

   PutFieldOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap) {
      final Object val = stackFrame.pop();
      final Object obj = stackFrame.pop();

      heap.put(obj, fieldKey(fieldInsnNode), val);
   }

   @Override
   public String toString() {
      return "PUTFIELD " + fieldKey(fieldInsnNode);
   }
}