package com.lexicalscope.symb.classloading.asm;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.DeclaredFields;
import com.lexicalscope.symb.vm.j.j.klass.DeclaredMethods;
import com.lexicalscope.symb.vm.j.j.klass.Fields;
import com.lexicalscope.symb.vm.j.j.klass.Methods;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SField;
import com.lexicalscope.symb.vm.j.j.klass.SFieldName;
import com.lexicalscope.symb.vm.j.j.klass.SMethod;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class AsmSClass implements SClass {
   private final String klassName;
   private final Set<SClass> superTypes = new HashSet<>();

   private final DeclaredFields declaredFields;
   private final DeclaredMethods declaredMethods;
   private final Fields fields;
   private final Methods methods;

   private final URL loadedFromUrl;
   private final SClass superclass;

   public AsmSClass(
         final URL loadedFromUrl,
         final String klassName,
         final SClass superclass,
         final List<SClass> interfaces,
         final DeclaredFields declaredFields,
         final DeclaredMethods declaredMethods) {
      this.loadedFromUrl = loadedFromUrl;
      this.superclass = superclass;
      this.klassName = klassName;

      this.declaredFields = declaredFields;
      this.declaredMethods = declaredMethods;
      this.fields = (superclass == null ? new Fields() : superclass.fields()).extend(klassName, declaredFields);
      this.methods = (superclass == null ? new Methods(klassName) : superclass.methods()).extend(klassName, declaredMethods);

      superTypes.add(this);
      if (superclass != null) {
         superTypes.addAll(superclass.superTypes());

      }

      for (final SClass interfac3 : interfaces) {
         superTypes.addAll(interfac3.superTypes());
      }
   }

   @Override
   public SMethod virtualMethod(final SMethodDescriptor sMethodName) {
      return methods.resolve(sMethodName);
   }

   @Override
   public SMethod declaredMethod(final String name, final String desc) {
      return declaredMethod(new AsmSMethodName(this.klassName, name, desc));
   }

   @Override
   public SMethod declaredMethod(final SMethodName sMethodName) {
      return methods.findDefined(sMethodName);
   }

   @Override
   public boolean hasStaticInitialiser() {
      return methods.hasStatic(new AsmSMethodName(klassName, JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC));
   }

   @Override public int allocateSize() {
      return fields.count() + OBJECT_PREAMBLE;
   }

   @Override
   public int fieldIndex(final SFieldName name) {
      return fields.indexOf(name) + OBJECT_PREAMBLE;
   }

   @Override public SField fieldAtIndex(final int index) {
      return fields.get(index - OBJECT_PREAMBLE);
   }

   @Override
   public boolean hasField(final SFieldName name) {
      return fields.contains(name);
   }

   private ArrayList<Object> fieldInit;
   @Override
   public List<Object> fieldInit() {
      if(fieldInit == null) {
         fieldInit = new ArrayList<>();
         if(superclass != null) {
            fieldInit.addAll(superclass.fieldInit());
         }
         fieldInit.addAll(declaredFields.fieldInit());
      }
      return fieldInit;
   }

   @Override public int staticFieldCount() {
      return declaredFields.staticCount();
   }

   @Override
   public int staticFieldIndex(final SFieldName name) {
      return declaredFields.staticFieldIndex(name) + STATICS_PREAMBLE;
   }

   @Override
   public boolean hasStaticField(final SFieldName name) {
      return declaredFields.containsStaticField(name);
   }

   @Override
   public String name() {
      return klassName;
   }

   @Override
   public SClass superclass() {
      return superclass;
   }

   @Override
   public Allocatable statics() {
      return new Allocatable() {
         @Override public int allocateSize() {
            return staticFieldCount() + STATICS_PREAMBLE;
         }
      };
   }

   @Override
   public boolean instanceOf(final SClass other) {
      return other == this || superTypes.contains(other);
   }

   @Override
   public URL loadedFrom() {
      return loadedFromUrl;
   }

   @Override public String toString() {
      return String.format("%s s<%s> <%s>", name(), declaredFields.staticFieldsToString(), fields);
   }

   @Override public boolean isArray() {
      return klassName.startsWith("[");
   }

   @Override public boolean isKlassKlass() {
      return name().equals(Type.getInternalName(Class.class));
   }

   @Override public Fields fields() {
      return fields;
   }

   @Override public Collection<SClass> superTypes() {
      return superTypes;
   }

   @Override public Methods methods() {
      return methods;
   }
}
