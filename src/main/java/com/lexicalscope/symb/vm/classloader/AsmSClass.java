package com.lexicalscope.symb.vm.classloader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.instructions.Instructions;

public final class AsmSClass implements SClass {
   private final Set<SClass> superTypes = new HashSet<>();

   private final List<FieldNode> fields;
   private final TreeMap<SFieldName, Integer> fieldMap;
   private final TreeMap<SFieldName, Integer> staticFieldMap;
   private final TreeMap<SMethodName, AsmSMethod> methodMap;
   private final TreeMap<SVirtualMethodName, AsmSMethod> virtuals;

   private final URL loadedFromUrl;
   private final ClassNode classNode;
   private final Instructions instructions;
   private final int subclassOffset;
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
         final SClassBuilder sClassBuilder) {
      this.classLoader = classLoader;
      this.instructions = instructions;
      this.loadedFromUrl = loadedFromUrl;
      this.classNode = classNode;
      this.superclass = superclass;

      this.staticFieldMap = new TreeMap<>();
      this.fields = new ArrayList<>();
      this.fieldMap = new TreeMap<>();
      this.fieldInit = new ArrayList<>();
      this.methodMap = new TreeMap<>();
      this.virtuals = new TreeMap<>();

      superTypes.add(this);

      if (superclass != null) {
         final TreeMap<SFieldName, Integer> superFieldMap = superclass.fieldMap;
         for (final Entry<SFieldName, Integer> superField : superFieldMap.entrySet()) {
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
      fieldcount = (superclass == null ? 0 : superclass.fieldcount) + sClassBuilder.declaredFieldMap.size();
      subclassOffset = sClassBuilder.classStartOffset + sClassBuilder.declaredFieldMap.size();
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

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#resolve(com.lexicalscope.symb.vm.classloader.SMethodName)
    */
   @Override
   public SMethodName resolve(final SMethodName sMethodName) {
      final SVirtualMethodName methodKey = new SVirtualMethodName(sMethodName.name(), sMethodName.desc());
      assert virtuals.containsKey(methodKey) : methodKey + " not in " + virtuals;
      return virtuals.get(methodKey).name();
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#staticMethod(java.lang.String, java.lang.String)
    */
   @Override
   public SMethod staticMethod(final String name, final String desc) {
      final SMethod result = methodMap.get(new SMethodName(classNode.name, name, desc));
      if (result == null) {
         throw new SMethodNotFoundException(name, desc);
      }
      return result;
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#hasStaticInitialiser()
    */
   @Override
   public boolean hasStaticInitialiser() {
      return methodMap.containsKey(new SMethodName(classNode.name, JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC));
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#fieldCount()
    */
   @Override public int allocateSize() {
      return fieldcount + OBJECT_PREAMBLE;
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#fieldIndex(com.lexicalscope.symb.vm.classloader.SFieldName)
    */
   @Override
   public int fieldIndex(final SFieldName name) {
      assert fieldMap.containsKey(name) : "cannot find " + name + " in " + fieldMap;
      return fieldMap.get(name) + OBJECT_PREAMBLE;
   }

   @Override public String fieldDescAtIndex(final int index) {
      return fields.get(index - OBJECT_PREAMBLE).desc;
   }

   @Override public String fieldNameAtIndex(final int index) {
      return fields.get(index - OBJECT_PREAMBLE).name;
   }

   @Override
   public boolean hasField(final SFieldName name) {
      return fieldMap.containsKey(name);
   }

   @Override
   public List<Object> fieldInit() {
      return fieldInit;
   }

   @Override
   public int staticFieldCount() {
      return staticFieldMap.size();
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#staticFieldIndex(com.lexicalscope.symb.vm.classloader.SFieldName)
    */
   @Override
   public int staticFieldIndex(final SFieldName name) {
      return staticFieldMap.get(name) + STATICS_PREAMBLE;
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#hasStaticField(com.lexicalscope.symb.vm.classloader.SFieldName)
    */
   @Override
   public boolean hasStaticField(final SFieldName name) {
      return staticFieldMap.containsKey(name);
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#name()
    */
   @Override
   public String name() {
      return classNode.name;
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#superclass()
    */
   @Override
   public Object superclass() {
      return superclass;
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#statics()
    */
   @Override
   public Allocatable statics() {
      return new Allocatable() {
         @Override public int allocateSize() {
            return staticFieldCount() + STATICS_PREAMBLE;
         }
      };
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#instanceOf(com.lexicalscope.symb.vm.classloader.SClass)
    */
   @Override
   public boolean instanceOf(final SClass other) {
      return other == this || superTypes.contains(other);
   }

   /* (non-Javadoc)
    * @see com.lexicalscope.symb.vm.classloader.SClass#loadedFrom()
    */
   @Override
   public URL loadedFrom() {
      return loadedFromUrl;
   }

   @Override public String toString() {
      return String.format("%s s<%s> <%s>", name(), staticFieldMap, fieldMap);
   }

   private static class SClassBuilder {
      private final SClassLoader classLoader;
      private final int classStartOffset;
      private final List<FieldNode> declaredFields = new ArrayList<>();
      private final TreeMap<SFieldName, Integer> declaredFieldMap = new TreeMap<>();
      private final TreeMap<SFieldName, Integer> declaredStaticFieldMap = new TreeMap<>();
      private final List<Object> declaredFieldInit = new ArrayList<>();

      public SClassBuilder(final SClassLoader classLoader, final AsmSClass superclass) {
         this.classLoader = classLoader;
         classStartOffset = superclass == null ? 0 : superclass.subclassOffset;
      }

      private void initialiseFieldMaps(final ClassNode classNode) {
         final List<?> fields = classNode.fields;
         int staticOffset = 0;
         int dynamicOffset = 0;
         for (int i = 0; i < fields.size(); i++) {
            final FieldNode fieldNode = (FieldNode) fields.get(i);
            final SFieldName fieldName = new SFieldName(classNode.name, fieldNode.name);
            if ((fieldNode.access & Opcodes.ACC_STATIC) != 0) {
               declaredStaticFieldMap.put(fieldName, staticOffset);
               staticOffset++;
            } else {
               declaredFields.add(fieldNode);
               declaredFieldMap.put(fieldName, dynamicOffset + classStartOffset);
               declaredFieldInit.add(classLoader.init(fieldNode.desc));
               dynamicOffset++;
            }
         }
      }
   }

   public static AsmSClass newSClass(final SClassLoader classLoader, final Instructions instructions, final URL loadedFromUrl, final ClassNode classNode, final AsmSClass superclass, final List<AsmSClass> interfaces) {
      final SClassBuilder sClassBuilder = new SClassBuilder(classLoader, superclass);
      sClassBuilder.initialiseFieldMaps(classNode);
      return new AsmSClass(classLoader, instructions, loadedFromUrl, classNode, superclass, interfaces, sClassBuilder);
   }

   @Override public boolean isArray() {
      return false;
   }
}
