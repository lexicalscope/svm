package com.lexicalscope.symb.vm.instructions.ops;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.classloader.SClass;

public final class DefineClassOp implements Op<List<SClass>> {
   public static final List<String> primitives = Arrays.asList(
         getInternalName(boolean.class),
         getInternalName(char.class),
         getInternalName(byte.class),
         getInternalName(short.class),
         getInternalName(int.class),
         getInternalName(long.class),
         getInternalName(float.class),
         getInternalName(double.class),
         getInternalName(boolean[].class),
         getInternalName(char[].class),
         getInternalName(byte[].class),
         getInternalName(short[].class),
         getInternalName(int[].class),
         getInternalName(long[].class),
         getInternalName(float[].class),
         getInternalName(double[].class),
         getInternalName(Object[].class));
   private final List<String> klassNames;

   public DefineClassOp(final String klassName) {
      this(Arrays.asList(klassName));
   }

   public DefineClassOp(final List<String> klassNames) {
      this.klassNames = klassNames;
   }

   @Override public List<SClass> eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
      final List<SClass> results = new ArrayList<>();
      for (final String klassName : klassNames) {
         if (!statics.isDefined(klassName)) {
            if(isPrimitive(klassName)) {
               final SClass klass = statics.definePrimitiveClass(klassName);
               DefineClassOp.allocateStatics(heap, statics, statics.classClass(), klass);
               // primitive classes do not need initialisation
            }
            else
            {
               results.addAll(statics.defineClass(klassName));
            }
         }
      }
      if(!results.isEmpty()) {
         for (final SClass klass : results) {
            allocateStatics(heap, statics, statics.classClass(), klass);
         }
      }
      return results;
   }

   private boolean isPrimitive(final String klassName) {
      return primitives.contains(klassName);
   }

   static void allocateStatics(final Heap heap, final Statics statics, final SClass classClass, final SClass klass) {
      if(klass.statics().allocateSize() > 0) {
         final Object staticsAddress = heap.newObject(klass.statics());
         heap.put(staticsAddress, SClass.OBJECT_CLASS_OFFSET, classClass);
         statics.staticsAt(klass, staticsAddress);
      }
   }

   @Override public String toString() {
      return "Define Classes " + klassNames;
   }
}