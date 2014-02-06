package com.lexicalscope.svm.j.instruction.concrete.klass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lexicalscope.svm.j.instruction.concrete.object.NewObjectOp;
import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.Op;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.StaticsMarker;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SFieldName;

public final class DefineClassOp implements Op<List<SClass>> {
   private final List<String> klassNames;

   public DefineClassOp(final String klassName) {
      this(Arrays.asList(klassName));
   }

   public DefineClassOp(final List<String> klassNames) {
      this.klassNames = klassNames;
   }

   @Override public List<SClass> eval(final State ctx) {
         final List<SClass> results = new ArrayList<>();
         for (final String klassName : klassNames) {
            if (!ctx.isDefined(klassName)) {
               final List<SClass> klasses = ctx.defineClass(klassName);

               for (final SClass klass : klasses) {
                  allocate(ctx, klass);
               }

               results.addAll(klasses);
            }
         }
         return results;
   }

   public static final SFieldName internalClassPointer = new SFieldName(JavaConstants.CLASS_CLASS, "*internalClassPointer");
   static void allocateClass(final State ctx, final SClass klass) {
      final SClass classClass = ctx.classClass();
      final Object classAddress = NewObjectOp.allocateObject(ctx, classClass);
      ctx.put(classAddress,  classClass.fieldIndex(internalClassPointer), klass);
      ctx.classAt(klass, classAddress);
   }

   static void allocateStatics(final State ctx, final StaticsMarker staticsMarker, final SClass klass) {
      if(klass.statics().allocateSize() > 0) {
         final Object staticsAddress = ctx.newObject(klass.statics());
         ctx.put(staticsAddress, SClass.OBJECT_MARKER_OFFSET, staticsMarker);
         ctx.staticsAt(klass, staticsAddress);
      }
   }

   static void allocate(final State ctx, final SClass klass) {
      DefineClassOp.allocateClass(ctx, klass);
      DefineClassOp.allocateStatics(ctx, ctx.staticsMarker(klass), klass);
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.synthetic();
   }

   @Override public String toString() {
      return "Define Classes " + klassNames;
   }
}