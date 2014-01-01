package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class DefinePrimitiveClassOp implements Op<Boolean> {
   private final String klassName;

   public DefinePrimitiveClassOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public Boolean eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      if (!statics.isDefined(klassName)) {
         final SClass klass = statics.definePrimitiveClass(klassName);
         final SClass classClass = statics.classClass();
         DefineClassOp.allocateStatics(heap, statics, classClass, klass);
      }
      return false;
   }

   @Override public String toString() {
      return "Define Primitive Class " + klassName;
   }
}