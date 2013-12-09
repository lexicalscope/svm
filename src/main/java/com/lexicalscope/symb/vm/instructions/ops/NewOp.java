package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

final class NewOp implements Vop {
   private final SClass klass;

   NewOp(final SClassLoader classLoader, final TypeInsnNode typeInsnNode) {
      this.klass = classLoader.load(typeInsnNode.desc);
   }

   @Override
   public void eval(final StackFrame stackFrame, final Heap heap) {
      stackFrame.push(heap.newObject(klass));
   }

   @Override
   public String toString() {
      return String.format("NEW %s", klass.name());
   }
}