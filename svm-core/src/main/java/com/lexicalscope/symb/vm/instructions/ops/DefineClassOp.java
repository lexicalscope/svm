package com.lexicalscope.symb.vm.instructions.ops;

import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.StateImpl;
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

   private boolean isPrimitive(final String klassName) {
      return primitives.contains(klassName);
   }

   @Override public List<SClass> eval(final StateImpl ctx) {
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

   private static void allocateClass(final StateImpl ctx, final SClass klass) {
      final SClass classClass = ctx.classClass();
      final Object classAddress = NewObjectOp.allocateObject(ctx, classClass);
      ctx.put(classAddress,  classClass.fieldIndex(AsmSClass.internalClassPointer), klass);
      ctx.classAt(klass, classAddress);
   }

   private static void allocateStatics(final StateImpl ctx, final StaticsMarker staticsMarker, final SClass klass) {
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