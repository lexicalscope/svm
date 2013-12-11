package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class ObjectPoolLoad implements Vop {
   private final Type type;

   public ObjectPoolLoad(final Type type) {
      this.type = type;
   }

   @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final SClass klass = statics.load(type.getInternalName());
      stackFrame.push(statics.whereMyStaticsAt(klass));
   }

   @Override public String toString() {
      return "LDC object " + type.getInternalName();
   }
}
