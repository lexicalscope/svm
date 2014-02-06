package com.lexicalscope.svm.j.instruction.concrete.klass;

import static com.lexicalscope.svm.j.instruction.concrete.klass.DefineClassOp.allocate;
import static org.objectweb.asm.Type.getInternalName;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.Op;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public final class DefinePrimitiveClassesOp implements Op<List<SClass>> {
   public static final Map<String, String> primitivesMap = new LinkedHashMap<String, String>(){{
         put(getInternalName(boolean.class), getInternalName(boolean[].class));
         put(getInternalName(char.class), getInternalName(char[].class));
         put(getInternalName(byte.class), getInternalName(byte[].class));
         put(getInternalName(short.class), getInternalName(short[].class));
         put(getInternalName(int.class), getInternalName(int[].class));
         put(getInternalName(long.class), getInternalName(long[].class));
         put(getInternalName(float.class), getInternalName(float[].class));
         put(getInternalName(double.class), getInternalName(double[].class));
   }};

   public static final List<String> primitives = new ArrayList<String>(){{
         addAll(primitivesMap.keySet());
         addAll(primitivesMap.values());
         add(getInternalName(Object[].class));
   }};

   private final DefineClassOp op;

   public DefinePrimitiveClassesOp(final DefineClassOp op) {
      this.op = op;
   }

   @Override public List<SClass> eval(final State ctx) {
      final List<SClass> result = op.eval(ctx);
      for (final Entry<String, String> primitive : primitivesMap.entrySet()) {
         final String primitiveName = primitive.getKey();
         final String primitiveArrayName = primitive.getValue();

         assert !ctx.isDefined(primitiveName) : primitiveName;
         assert !ctx.isDefined(primitiveArrayName) : primitiveArrayName;

         // primitive classes do not need initialisation
         allocate(ctx, ctx.definePrimitiveClass(primitiveName));
         allocate(ctx, ctx.definePrimitiveClass(primitiveArrayName));
      }
      allocate(ctx, ctx.definePrimitiveClass(getInternalName(Object[].class)));
      return result;
   }

   @Override public String toString() {
      return "Define Primitives";
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.synthetic();
   }

   public static boolean primitivesContains(final String klassName) {
      return primitives.contains(klassName);
   }
}