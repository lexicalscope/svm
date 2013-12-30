package com.lexicalscope.symb.vm.classloader;

import java.net.URL;
import java.util.List;

public interface SClass extends Allocatable {
   public static final int STATICS_PREAMBLE = 1;
   public static final int OBJECT_PREAMBLE = 1;
   public static final int OBJECT_CLASS_OFFSET = 0;

   SMethodName resolve(SMethodName sMethodName);

   SMethod staticMethod(String name, String desc);

   boolean hasStaticInitialiser();

   @Override
   int fieldCount();

   int fieldIndex(SFieldName name);

   boolean hasField(SFieldName name);

   List<Object> fieldInit();

   int staticFieldCount();

   int staticFieldIndex(SFieldName name);

   boolean hasStaticField(SFieldName name);

   String name();

   Object superclass();

   Allocatable statics();

   boolean instanceOf(SClass other);

   URL loadedFrom();
}