package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class GetStaticOp implements Vop {
   private final int offset;
   private final FieldInsnNode fieldInsnNode;

   GetStaticOp(final SClass klass, final FieldInsnNode fieldInsnNode) {
      this.offset = klass.staticFieldIndex(new SFieldName(fieldInsnNode.owner, fieldInsnNode.name));
      this.fieldInsnNode = fieldInsnNode;
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap) {
      final Object obj = stackFrame.pop();

      stackFrame.push(heap.get(obj, offset));
   }

   @Override
   public String toString() {
      return "GETSTATIC " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}