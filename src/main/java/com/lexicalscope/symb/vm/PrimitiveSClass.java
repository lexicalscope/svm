package com.lexicalscope.symb.vm;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public final class PrimitiveSClass implements SClass {
   private final String klassName;

   public PrimitiveSClass(final String klassName) {
      this.klassName = klassName;
   }

   @Override public SMethodName resolve(final SMethodName sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public SMethod staticMethod(final String name, final String desc) {
      throw new UnsupportedOperationException();
   }

   @Override public boolean hasStaticInitialiser() {
      return false;
   }

   @Override public int allocateSize() {
      return 0;
   }

   @Override public int fieldIndex(final SFieldName name) {
      return 0;
   }

   @Override public String fieldDescAtIndex(final int i) {
      return null;
   }

   @Override public String fieldNameAtIndex(final int index) {
      return null;
   }

   @Override public boolean hasField(final SFieldName name) {
      return false;
   }

   @Override public List<Object> fieldInit() {
      return Collections.emptyList();
   }

   @Override public int staticFieldIndex(final SFieldName name) {
      return 0;
   }

   @Override public boolean hasStaticField(final SFieldName name) {
      return false;
   }

   @Override public String name() {
      return klassName;
   }

   @Override public Object superclass() {
      return null;
   }

   @Override public Allocatable statics() {
      return new Allocatable() {
         @Override public int allocateSize() {
            return STATICS_PREAMBLE;
         }
      };
   }

   @Override public boolean instanceOf(final SClass other) {
      return other == this;
   }

   @Override public URL loadedFrom() {
      throw new UnsupportedOperationException();
   }

   @Override public boolean isArray() {
      return klassName.startsWith("[");
   }

   @Override public boolean isKlassKlass() {
      return false;
   }

   @Override public boolean isPrimitive() {
      return true;
   }

   @Override public String toString() {
      return "class: " + name();
   }
}
