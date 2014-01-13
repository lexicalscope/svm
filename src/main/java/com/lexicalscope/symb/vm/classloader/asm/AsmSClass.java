package com.lexicalscope.symb.vm.classloader.asm;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.classloader.Allocatable;
import com.lexicalscope.symb.vm.classloader.AsmSMethod;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SField;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.classloader.SMethod;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.classloader.SMethodNotFoundException;
import com.lexicalscope.symb.vm.classloader.SVirtualMethodName;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class AsmSClass implements SClass {
   public static final SFieldName internalClassPointer = new SFieldName(JavaConstants.CLASS_CLASS, "*internalClassPointer");

   private final String klassName;
   private final Set<SClass> superTypes = new HashSet<>();

   private final DeclaredFields declaredFields;
   private final Fields fields;
   private final TreeMap<SFieldName, Integer> declaredStaticFieldMap;

   private final TreeMap<SFieldName, Integer> staticFieldMap;
   private final TreeMap<SMethodName, AsmSMethod> methodMap;
   private final TreeMap<SVirtualMethodName, AsmSMethod> virtuals;

   private final URL loadedFromUrl;
   private final Instructions instructions;
   private final SClass superclass;
   private final SClassLoader classLoader;

   // TODO[tim]: far too much work in this constructor
   public AsmSClass(
         final SClassLoader classLoader,
         final Instructions instructions,
         final URL loadedFromUrl,
         final ClassNode classNode,
         final AsmSClass superclass,
         final List<AsmSClass> interfaces,
         final AsmSClassBuilder sClassBuilder) {
      this.classLoader = classLoader;
      this.instructions = instructions;
      this.loadedFromUrl = loadedFromUrl;
      this.superclass = superclass;
      this.klassName = classNode.name;

      this.declaredFields = sClassBuilder.declaredFields();
      this.declaredStaticFieldMap = sClassBuilder.declaredStaticFieldMap;

      this.staticFieldMap = new TreeMap<>();
      this.methodMap = new TreeMap<>();
      this.virtuals = new TreeMap<>();

      superTypes.add(this);

      this.fields = superclass == null ? new Fields() : superclass.fields.copy(classNode.name, declaredFields);
      if (superclass != null) {
         superTypes.addAll(superclass.superTypes);
         virtuals.putAll(superclass.virtuals);
      }

      for (final AsmSClass interfac3 : interfaces) {
         superTypes.addAll(interfac3.superTypes);
      }

      staticFieldMap.putAll(sClassBuilder.declaredStaticFieldMap);

      initialiseMethodMap(classNode);

   }

   private void initialiseMethodMap(final ClassNode classNode) {
      for (final MethodNode method : methods(classNode)) {
         final SMethodName methodName = new SMethodName(klassName, method.name, method.desc);
         final AsmSMethod smethod = new AsmSMethod(classLoader, this, methodName, instructions, method);
         methodMap.put(methodName, smethod);
         virtuals.put(new SVirtualMethodName(method.name, method.desc), smethod);
      }
   }

   @SuppressWarnings("unchecked") private List<MethodNode> methods(final ClassNode classNode) {
      return classNode.methods;
   }

   @Override
   public SMethodName resolve(final SMethodName sMethodName) {
      final SVirtualMethodName methodKey = new SVirtualMethodName(sMethodName.name(), sMethodName.desc());
      assert virtuals.containsKey(methodKey) : methodKey + " not in " + virtuals;
      return virtuals.get(methodKey).name();
   }

   @Override
   public SMethod staticMethod(final String name, final String desc) {
      final SMethod result = methodMap.get(new SMethodName(this.klassName, name, desc));
      if (result == null) {
         throw new SMethodNotFoundException(this, name, desc);
      }
      return result;
   }

   @Override
   public boolean hasStaticInitialiser() {
      return methodMap.containsKey(new SMethodName(klassName, JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC));
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

   public int staticFieldCount() {
      return staticFieldMap.size();
   }

   @Override
   public int staticFieldIndex(final SFieldName name) {
      return staticFieldMap.get(name) + STATICS_PREAMBLE;
   }

   @Override
   public boolean hasStaticField(final SFieldName name) {
      return staticFieldMap.containsKey(name);
   }

   @Override
   public String name() {
      return klassName;
   }

   @Override
   public Object superclass() {
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
      return String.format("%s s<%s> <%s>", name(), staticFieldMap, fields);
   }

   @Override public boolean isArray() {
      return false;
   }

   @Override public boolean isKlassKlass() {
      return name().equals(Type.getInternalName(Class.class));
   }

   @Override public boolean isPrimitive() {
      return false;
   }
}
