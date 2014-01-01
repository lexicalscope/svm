package com.lexicalscope.symb.vm.instructions.ops;

import static org.objectweb.asm.Type.getInternalName;

import java.util.Collections;
import java.util.List;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class DefineClassOp implements Op<List<SClass>> {
   private final String klassName;

   public DefineClassOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public List<SClass> eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      if (!statics.isDefined(klassName)) {
         final List<SClass> klasses = statics.defineClass(klassName);

         final SClass classClass = statics.load(getInternalName(Class.class));
         for (final SClass klass : klasses) {
            allocateStatics(heap, statics, classClass, klass);
         }
         return klasses;
      }
      return Collections.emptyList();
   }

   static void allocateStatics(final Heap heap, final Statics statics, final SClass classClass, final SClass klass) {
      if(klass.statics().allocateSize() > 0) {
         final Object staticsAddress = heap.newObject(klass.statics());
         heap.put(staticsAddress, SClass.OBJECT_CLASS_OFFSET, classClass);
         statics.staticsAt(klass, staticsAddress);
      }
   }

   @Override public String toString() {
      return "Define Class " + klassName;
   }
}