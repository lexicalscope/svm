package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class GetFieldOp implements HeapVop {
   private final int offset;
   private final FieldInsnNode fieldInsnNode;

   GetFieldOp(final SClass klass, final FieldInsnNode fieldInsnNode) {
      this.offset = klass.fieldIndex(new SFieldName(fieldInsnNode.owner, fieldInsnNode.name));
      this.fieldInsnNode = fieldInsnNode;
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap) {
      final Object obj = stackFrame.pop();

      stackFrame.push(heap.get(obj, offset));
   }

   @Override
   public String toString() {
      return "GETFIELD " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}