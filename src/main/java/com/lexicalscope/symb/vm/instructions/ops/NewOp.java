package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

final class NewOp implements Vop {
   private final TypeInsnNode typeInsnNode;

   NewOp(final TypeInsnNode typeInsnNode) {
      this.typeInsnNode = typeInsnNode;
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap, final Statics statics) {
      // TODO[tim]: linking should remove this
      final SClass klass = statics.load(typeInsnNode.desc);
      stackFrame.push(heap.newObject(klass));
   }

   @Override
   public String toString() {
      return String.format("NEW %s", typeInsnNode.desc);
   }
}