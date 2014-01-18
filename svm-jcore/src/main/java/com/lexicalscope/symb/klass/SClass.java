package com.lexicalscope.symb.klass;

import java.net.URL;
import java.util.List;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.vm.MethodResolver;

public interface SClass extends Allocatable, MethodResolver {
   public static final int STATICS_PREAMBLE = 1;
   public static final int OBJECT_PREAMBLE = 1;
   public static final int OBJECT_MARKER_OFFSET = 0;

   SMethod declaredMethod(String name, String desc);

   boolean hasStaticInitialiser();

   @Override
   int allocateSize();

   int fieldIndex(SFieldName name);
   SField fieldAtIndex(int index);
   boolean hasField(SFieldName name);
   Fields fields();

   List<Object> fieldInit();

   int staticFieldIndex(SFieldName name);
   boolean hasStaticField(SFieldName name);

   Object superclass();

   Allocatable statics();

   boolean instanceOf(SClass other);

   URL loadedFrom();

   boolean isArray();
   boolean isPrimitive();
   boolean isKlassKlass();
}