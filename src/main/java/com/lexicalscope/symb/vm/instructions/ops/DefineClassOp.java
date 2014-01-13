package com.lexicalscope.symb.vm.instructions.ops;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.StaticsMarker;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.asm.AsmSClass;

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
            final List<SClass> klasses;
            if(isPrimitive(klassName)) {
               klasses = ImmutableList.of(statics.definePrimitiveClass(klassName));
               // primitive classes do not need initialisation
            }
            else
            {
               klasses = statics.defineClass(klassName);
               results.addAll(klasses);
            }

            for (final SClass klass : klasses) {
               allocateClass(heap, statics, klass);
               allocateStatics(heap, statics, statics.staticsMarker(klass), klass);
            }
         }
      }
      return results;
   }

   private boolean isPrimitive(final String klassName) {
      return primitives.contains(klassName);
   }

   static void allocateClass(final Heap heap, final Statics statics, final SClass klass) {
      final SClass classClass = statics.classClass();
      final Object classAddress = NewOp.allocateObject(heap, classClass);
      heap.put(classAddress,  classClass.fieldIndex(AsmSClass.internalClassPointer), klass);
      statics.classAt(klass, classAddress);
   }
   static void allocateStatics(final Heap heap, final Statics statics, final StaticsMarker staticsMarker, final SClass klass) {
      if(klass.statics().allocateSize() > 0) {
         final Object staticsAddress = heap.newObject(klass.statics());
         heap.put(staticsAddress, SClass.OBJECT_MARKER_OFFSET, staticsMarker);
         statics.staticsAt(klass, staticsAddress);
      }
   }

   @Override public String toString() {
      return "Define Classes " + klassNames;
   }

   public static boolean primitivesContains(final String klassName) {
      return primitives.contains(klassName);
   }
}