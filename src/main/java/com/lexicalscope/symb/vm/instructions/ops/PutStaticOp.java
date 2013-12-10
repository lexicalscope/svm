package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class PutStaticOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;

   PutStaticOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
   }

   @Override
   public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final SClass klass = statics.load(fieldInsnNode.owner);
      final Object staticsAddress = statics.whereMyStaticsAt(klass);
      final int offset = klass.staticFieldIndex(name);

      heap.put(staticsAddress, offset, stackFrame.pop());
   }

   @Override
   public String toString() {
      return "PUTSTATIC " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}