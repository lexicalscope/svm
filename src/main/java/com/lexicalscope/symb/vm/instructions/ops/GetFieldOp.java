package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class GetFieldOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SClassLoader classLoader;
   private final SFieldName name;

   public GetFieldOp(final SClassLoader classLoader, final FieldInsnNode fieldInsnNode) {
      this.classLoader = classLoader;
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
   }

   @Override public void eval(final StackFrame stackFrame, final Heap heap) {
      // TODO[tim]: link should remove this
      final int offset = classLoader.load(fieldInsnNode.owner).fieldIndex(name);
      final Object obj = stackFrame.pop();

      stackFrame.push(heap.get(obj, offset));
   }

   @Override
   public String toString() {
      return "GETFIELD " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}