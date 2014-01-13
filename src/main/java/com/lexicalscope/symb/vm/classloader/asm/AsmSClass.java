package com.lexicalscope.symb.vm.classloader.asm;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
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

   private final Set<SClass> superTypes = new HashSet<>();

   private final List<SField> declaredFields;
   private final TreeMap<SFieldName, Integer> declaredFieldMap;
   private final TreeMap<SFieldName, Integer> declaredStaticFieldMap;

   private final List<SField> fields;
   private final TreeMap<SFieldName, Integer> fieldMap;
   private final TreeMap<SFieldName, Integer> staticFieldMap;
   private final TreeMap<SMethodName, AsmSMethod> methodMap;
   private final TreeMap<SVirtualMethodName, AsmSMethod> virtuals;

   private final URL loadedFromUrl;
   private final ClassNode classNode;
   private final Instructions instructions;
   final int subclassOffset;
   private final SClass superclass;
   private final SClassLoader classLoader;
   private final ArrayList<Object> fieldInit;

   private final int fieldcount;

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
      this.classNode = classNode;
      this.superclass = superclass;

      this.declaredFields = sClassBuilder.declaredFields;
      this.declaredFieldMap = sClassBuilder.declaredFieldMap;
      this.declaredStaticFieldMap = sClassBuilder.declaredStaticFieldMap;
      this.subclassOffset = sClassBuilder.subclassOffset();
      this.fieldcount = (superclass == null ? 0 : superclass.fieldcount) + sClassBuilder.declaredFieldMap.size();

      this.staticFieldMap = new TreeMap<>();
      this.fields = new ArrayList<>();
      this.fieldMap = new TreeMap<>();
      this.fieldInit = new ArrayList<>();
      this.methodMap = new TreeMap<>();
      this.virtuals = new TreeMap<>();

      superTypes.add(this);

      if (superclass != null) {
         for (final Entry<SFieldName, Integer> superField : superclass.fieldMap.entrySet()) {
            // if a field is not shadowed, looking it up in this class should resolve the superclass field
            fieldMap.put(new SFieldName(classNode.name, superField.getKey().getName()), superField.getValue());
         }
         fields.addAll(superclass.fields);
         fieldMap.putAll(superclass.fieldMap);
         fieldInit.addAll(superclass.fieldInit);
         superTypes.addAll(superclass.superTypes);
         virtuals.putAll(superclass.virtuals);
      }

      for (final AsmSClass interfac3 : interfaces) {
         superTypes.addAll(interfac3.superTypes);
      }

      fields.addAll(sClassBuilder.declaredFields);
      fieldInit.addAll(sClassBuilder.declaredFieldInit);
      fieldMap.putAll(sClassBuilder.declaredFieldMap);
      staticFieldMap.putAll(sClassBuilder.declaredStaticFieldMap);

      initialiseMethodMap();
   }

   private void initialiseMethodMap() {
      for (final MethodNode method : methods()) {
         final SMethodName methodName = new SMethodName(classNode.name, method.name, method.desc);
         final AsmSMethod smethod = new AsmSMethod(classLoader, this, methodName, instructions, method);
         methodMap.put(methodName, smethod);
         virtuals.put(new SVirtualMethodName(method.name, method.desc), smethod);
      }
   }

   @SuppressWarnings("unchecked") private List<MethodNode> methods() {
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
      final SMethod result = methodMap.get(new SMethodName(classNode.name, name, desc));
      if (result == null) {
         throw new SMethodNotFoundException(this, name, desc);
      }
      return result;
   }

   @Override
   public boolean hasStaticInitialiser() {
      return methodMap.containsKey(new SMethodName(classNode.name, JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC));
   }

   @Override public int allocateSize() {
      return fieldcount + OBJECT_PREAMBLE;
   }

   @Override
   public int fieldIndex(final SFieldName name) {
      assert fieldMap.containsKey(name) : "cannot find " + name + " in " + fieldMap;
      return fieldMap.get(name) + OBJECT_PREAMBLE;
   }

   @Override public String fieldDescAtIndex(final int index) {
      return fields.get(index - OBJECT_PREAMBLE).desc();
   }

   @Override public String fieldNameAtIndex(final int index) {
      return fields.get(index - OBJECT_PREAMBLE).name();
   }

   @Override
   public boolean hasField(final SFieldName name) {
      return fieldMap.containsKey(name);
   }

   @Override
   public List<Object> fieldInit() {
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
      return classNode.name;
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
      return String.format("%s s<%s> <%s>", name(), staticFieldMap, fieldMap);
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
