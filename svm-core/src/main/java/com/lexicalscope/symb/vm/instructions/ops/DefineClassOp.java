package com.lexicalscope.symb.vm.instructions.ops;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.StaticsMarker;
import com.lexicalscope.symb.vm.Vm;
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

   @Override public List<SClass> eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
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

   private static void allocateClass(final Heap heap, final Statics statics, final SClass klass) {
      final SClass classClass = statics.classClass();
      final Object classAddress = NewObjectOp.allocateObject(heap, classClass);
      heap.put(classAddress,  classClass.fieldIndex(AsmSClass.internalClassPointer), klass);
      statics.classAt(klass, classAddress);
   }

   private static void allocateStatics(final Heap heap, final Statics statics, final StaticsMarker staticsMarker, final SClass klass) {
      if(klass.statics().allocateSize() > 0) {
         final Object staticsAddress = heap.newObject(klass.statics());
         heap.put(staticsAddress, SClass.OBJECT_MARKER_OFFSET, staticsMarker);
         statics.staticsAt(klass, staticsAddress);
      }
   }

   @Override public List<SClass> eval(final Context ctx) {
         final List<SClass> results = new ArrayList<>();
         for (final String klassName : klassNames) {
            if (!ctx.isDefined(klassName)) {
               final List<SClass> klasses;
               if(isPrimitive(klassName)) {
                  klasses = ImmutableList.of(ctx.definePrimitiveClass(klassName));
                  // primitive classes do not need initialisation
               }
               else
               {
                  klasses = ctx.defineClass(klassName);
                  results.addAll(klasses);
               }

               for (final SClass klass : klasses) {
                  allocateClass(ctx, klass);
                  allocateStatics(ctx, ctx.staticsMarker(klass), klass);
               }
            }
         }
         return results;
   }

   private static void allocateClass(final Context ctx, final SClass klass) {
      final SClass classClass = ctx.classClass();
      final Object classAddress = NewObjectOp.allocateObject(ctx, classClass);
      ctx.put(classAddress,  classClass.fieldIndex(AsmSClass.internalClassPointer), klass);
      ctx.classAt(klass, classAddress);
   }

   private static void allocateStatics(final Context ctx, final StaticsMarker staticsMarker, final SClass klass) {
      if(klass.statics().allocateSize() > 0) {
         final Object staticsAddress = ctx.newObject(klass.statics());
         ctx.put(staticsAddress, SClass.OBJECT_MARKER_OFFSET, staticsMarker);
         ctx.staticsAt(klass, staticsAddress);
      }
   }

   @Override public String toString() {
      return "Define Classes " + klassNames;
   }

   public static boolean primitivesContains(final String klassName) {
      return primitives.contains(klassName);
   }
}