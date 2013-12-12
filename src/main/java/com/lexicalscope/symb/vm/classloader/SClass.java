package com.lexicalscope.symb.vm.classloader;

import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class SClass implements Allocatable {
   public static final int STATICS_PREAMBLE = 1;
   public static final int OBJECT_PREAMBLE = 1;

   private final TreeMap<SFieldName, Integer> fieldMap;
   private final TreeMap<SFieldName, Integer> staticFieldMap;
   private final TreeMap<SMethodName, SMethod> methodMap;

   private final ClassNode classNode;
   private final Instructions instructions;
   private final int classStartOffset;
   private final int subclassOffset;
   private final Allocatable superclass;
   private final SClassLoader classLoader;

   // TODO[tim]: far to much work in this constructor
   public SClass(final SClassLoader classLoader, final Instructions instructions, final ClassNode classNode, final SClass superclass) {
      this.classLoader = classLoader;
      this.instructions = instructions;
      this.classNode = classNode;
      this.superclass = superclass;

      this.classStartOffset = superclass == null ? 0 : superclass.subclassOffset;
      this.staticFieldMap = new TreeMap<>();
      this.fieldMap = new TreeMap<>();
      this.methodMap = new TreeMap<>();

      if (superclass != null)
         fieldMap.putAll(superclass.fieldMap);

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
      return fieldMap.get(name) + OBJECT_PREAMBLE;
   }

   public boolean hasField(final SFieldName name) {
      return fieldMap.containsKey(name);
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

   @Override public String toString() {
      return String.format("%s s<%s> <%s>", name(), staticFieldMap, fieldMap);
   }
}
