package com.lexicalscope.symb.vm.j.j.klass;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.vm.j.MethodResolver;

public interface SClass extends Allocatable, MethodResolver {
   public static final int STATICS_PREAMBLE = 1;
   public static final int OBJECT_PREAMBLE = 1;
   public static final int OBJECT_MARKER_OFFSET = 0;

   String name();
   SClass superclass();

   boolean hasStaticInitialiser();
   Allocatable statics();

   SMethod declaredMethod(String name, String desc);

   int fieldIndex(SFieldName name);
   SField fieldAtIndex(int index);
   boolean hasField(SFieldName name);
   Fields fields();

   List<Object> fieldInit();

   int staticFieldIndex(SFieldName name);
   boolean hasStaticField(SFieldName name);
   int staticFieldCount();

   boolean instanceOf(SClass other);
   boolean isArray();
   boolean isKlassKlass();

   URL loadedFrom();

   Collection<SClass> superTypes();
   Methods methods();

   SClass componentType();
}