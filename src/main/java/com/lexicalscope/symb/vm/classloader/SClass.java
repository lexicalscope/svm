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

public final class SClass implements Allocatable {
   public static final int STATICS_PREAMBLE = 1;
   public static final int OBJECT_PREAMBLE = 1;
   public static final int OBJECT_CLASS_OFFSET = 0;

   private final Set<SClass> superTypes = new HashSet<>();

   private final TreeMap<SFieldName, Integer> fieldMap;
   private final TreeMap<SFieldName, Integer> staticFieldMap;
   private final TreeMap<SMethodName, SMethod> methodMap;

   private final URL loadedFromUrl;
   private final ClassNode classNode;
   private final Instructions instructions;
   private final int classStartOffset;
   private final int subclassOffset;
   private final SClass superclass;
   private final SClassLoader classLoader;
   private final ArrayList<Object> fieldInit;

   // TODO[tim]: far too much work in this constructor
   public SClass(
         final SClassLoader classLoader,
         final Instructions instructions,
         final URL loadedFromUrl,
         final ClassNode classNode,
         final SClass superclass,
         final List<SClass> interfaces) {
      this.classLoader = classLoader;
      this.instructions = instructions;
      this.loadedFromUrl = loadedFromUrl;
      this.classNode = classNode;
      this.superclass = superclass;

      this.classStartOffset = superclass == null ? 0 : superclass.subclassOffset;
      this.staticFieldMap = new TreeMap<>();
      this.fieldMap = new TreeMap<>();
      this.fieldInit = new ArrayList<>();
      this.methodMap = new TreeMap<>();

      superTypes.add(this);

      if (superclass != null) {
         final TreeMap<SFieldName, Integer> superFieldMap = superclass.fieldMap;
         for (final Entry<SFieldName, Integer> superField : superFieldMap.entrySet()) {
            fieldMap.put(new SFieldName(classNode.name, superField.getKey().getName()), superField.getValue());
         }
         fieldMap.putAll(superclass.fieldMap);
         fieldInit.addAll(superclass.fieldInit);
         superTypes.addAll(superclass.superTypes);
      }

      for (final SClass interfac3 : interfaces) {
         superTypes.addAll(interfac3.superTypes);
      }

      initialiseFieldMaps();
      initialiseMethodMap();
      subclassOffset = fieldMap.size();
   }

   private void initialiseMethodMap() {
      for (final MethodNode method : methods()) {
         final SMethodName methodName = new SMethodName(classNode.name, method.name, method.desc);
         methodMap.put(methodName, new SMethod(classLoader, methodName, instructions, method));
      }
   }

   private void initialiseFieldMaps() {
      final List<?> fields = classNode.fields;
      int staticOffset = 0;
      int dynamicOffset = 0;
      for (int i = 0; i < fields.size(); i++) {
         final FieldNode fieldNode = (FieldNode) fields.get(i);
         final SFieldName fieldName = new SFieldName(this.name(), fieldNode.name);
         if ((fieldNode.access & Opcodes.ACC_STATIC) != 0) {
            staticFieldMap.put(fieldName, staticOffset);
            staticOffset++;
         } else {
            fieldMap.put(fieldName, dynamicOffset + classStartOffset);
            fieldInit.add(classLoader.init(fieldNode.desc));
            dynamicOffset++;
         }
      }
   }

   @SuppressWarnings("unchecked") private List<MethodNode> methods() {
      return classNode.methods;
   }

   public SMethod staticMethod(final String name, final String desc) {
      final SMethod result = methodMap.get(new SMethodName(classNode.name, name, desc));
      if (result == null) {
         throw new SMethodNotFoundException(name, desc);
      }
      return result;
   }

   public boolean hasStaticInitialiser() {
      return methodMap.containsKey(new SMethodName(classNode.name, JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC));
   }

   @Override public int fieldCount() {
      return fieldMap.size() + OBJECT_PREAMBLE;
   }

   public int fieldIndex(final SFieldName name) {
      assert fieldMap.containsKey(name) : "cannot find " + name + " in " + fieldMap;
      return fieldMap.get(name) + OBJECT_PREAMBLE;
   }

   public boolean hasField(final SFieldName name) {
      return fieldMap.containsKey(name);
   }

   public List<Object> fieldInit() {
      return fieldInit;
   }

   public int staticFieldCount() {
      return staticFieldMap.size();
   }

   public int staticFieldIndex(final SFieldName name) {
      return staticFieldMap.get(name) + STATICS_PREAMBLE;
   }

   public boolean hasStaticField(final SFieldName name) {
      return staticFieldMap.containsKey(name);
   }

   public String name() {
      return classNode.name;
   }

   public Object superclass() {
      return superclass;
   }

   public Allocatable statics() {
      return new Allocatable() {
         @Override public int fieldCount() {
            return staticFieldCount() + STATICS_PREAMBLE;
         }
      };
   }

   public boolean instanceOf(final SClass other) {
      return other == this || superTypes.contains(other);
   }

   public URL loadedFrom() {
      return loadedFromUrl;
   }

   @Override public String toString() {
      return String.format("%s s<%s> <%s>", name(), staticFieldMap, fieldMap);
   }
}
