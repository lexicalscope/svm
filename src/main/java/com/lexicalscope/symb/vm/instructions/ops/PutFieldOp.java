package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.instructions.BaseInstructions.fieldKey;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;

final class PutFieldOp implements HeapVop {
   private final FieldInsnNode fieldInsnNode;
   private final int offset;

   PutFieldOp(final SClass klass, final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.offset = klass.fieldIndex(new SFieldName(fieldInsnNode.owner, fieldInsnNode.name));
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap) {
      final Object val = stackFrame.pop();
      final Object obj = stackFrame.pop();

      heap.put(obj, offset, val);
   }

   @Override
   public String toString() {
      return "PUTFIELD " + fieldKey(fieldInsnNode);
   }
}