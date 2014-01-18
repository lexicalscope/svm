package com.lexicalscope.symb.vm;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.klass.Fields;
import com.lexicalscope.symb.klass.Methods;
import com.lexicalscope.symb.klass.SClass;
import com.lexicalscope.symb.klass.SField;
import com.lexicalscope.symb.klass.SFieldName;
import com.lexicalscope.symb.klass.SMethod;
import com.lexicalscope.symb.klass.SMethodDescriptor;
import com.lexicalscope.symb.state.SMethodName;

public final class PrimitiveSClass implements SClass {
   private final String klassName;

   public PrimitiveSClass(final String klassName) {
      this.klassName = klassName;
   }

   @Override public SMethod virtualMethod(final SMethodDescriptor sMethodName) {
      throw new UnsupportedOperationException();
   }

   @Override public SMethod declaredMethod(final String name, final String desc) {
      throw new UnsupportedOperationException();
   }

   @Override public SMethod declaredMethod(final SMethodName sMethodName) {
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

   @Override public SField fieldAtIndex(final int index) {
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

   @Override public SClass superclass() {
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

   @Override public Fields fields() {
      return null;
   }

   @Override public String toString() {
      return "class: " + name();
   }

   @Override public List<SClass> superTypes() {
      return null;
   }

   @Override public Methods methods() {
      return null;
   }
}
