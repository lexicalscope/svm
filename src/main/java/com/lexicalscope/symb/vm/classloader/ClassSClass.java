package com.lexicalscope.symb.vm.classloader;

import java.net.URL;
import java.util.List;

public final class ClassSClass implements SClass {

   private final SClass asmClass;

   public ClassSClass(final SClass asmClass) {
      this.asmClass = asmClass;
   }

   @Override
   public SMethodName resolve(final SMethodName sMethodName) {
      return asmClass.resolve(sMethodName);
   }

   @Override
   public SMethod staticMethod(final String name, final String desc) {
      return asmClass.staticMethod(name, desc);
   }

   @Override
   public boolean hasStaticInitialiser() {
      return asmClass.hasStaticInitialiser();
   }

   @Override
   public int allocateSize() {
      return asmClass.allocateSize();
   }

   @Override
   public int fieldIndex(final SFieldName name) {
      return asmClass.fieldIndex(name);
   }

   @Override
   public String fieldDescAtIndex(final int index) {
      return asmClass.fieldDescAtIndex(index);
   }

   @Override
   public String fieldNameAtIndex(final int index) {
      return asmClass.fieldNameAtIndex(index);
   }

   @Override
   public boolean hasField(final SFieldName name) {
      return asmClass.hasField(name);
   }

   @Override
   public List<Object> fieldInit() {
      return asmClass.fieldInit();
   }

   @Override
   public int staticFieldIndex(final SFieldName name) {
      return asmClass.staticFieldIndex(name);
   }

   @Override
   public boolean hasStaticField(final SFieldName name) {
      return asmClass.hasStaticField(name);
   }

   @Override
   public String name() {
      return asmClass.name();
   }

   @Override
   public Object superclass() {
      return asmClass.superclass();
   }

   @Override
   public Allocatable statics() {
      return asmClass.statics();
   }

   @Override
   public boolean instanceOf(final SClass other) {
      return asmClass.instanceOf(other);
   }

   @Override
   public URL loadedFrom() {
      return asmClass.loadedFrom();
   }

   @Override
   public boolean isArray() {
      return asmClass.isArray();
   }

   @Override
   public boolean isPrimitive() {
      return asmClass.isPrimitive();
   }

   @Override
   public boolean isKlassKlass() {
      return asmClass.isKlassKlass();
   }

   @Override public String toString() {
      return asmClass.toString();
   }

   @Override public boolean equals(final Object obj) {
      if(this == obj) {
         return true;
      }
      if(obj != null && obj.getClass().equals(this.getClass())) {
         return ((ClassSClass) obj).asmClass.equals(this.asmClass);
      }
      return false;
   }
}
