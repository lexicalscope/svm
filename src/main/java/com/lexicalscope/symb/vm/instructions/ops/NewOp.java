package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.StackFrame;

final class NewOp implements HeapVop {
   private final TypeInsnNode typeInsnNode;

   NewOp(TypeInsnNode typeInsnNode) {
      this.typeInsnNode = typeInsnNode;
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap) {
      stackFrame.push(heap.newObject());
   }

   @Override
   public String toString() {
      return String.format("NEW %s", typeInsnNode.desc);
   }
}