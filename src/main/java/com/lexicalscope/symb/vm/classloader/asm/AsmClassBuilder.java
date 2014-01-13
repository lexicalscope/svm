package com.lexicalscope.symb.vm.classloader.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import com.lexicalscope.symb.vm.JavaConstants;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SFieldName;

class AsmClassBuilder {
   private final SClassLoader classLoader;
   private final int classStartOffset;
   int subclassOffset;
   final List<FieldNode> declaredFields = new ArrayList<>();
   final TreeMap<SFieldName, Integer> declaredFieldMap = new TreeMap<>();
   final TreeMap<SFieldName, Integer> declaredStaticFieldMap = new TreeMap<>();
   final List<Object> declaredFieldInit = new ArrayList<>();

   public AsmClassBuilder(final SClassLoader classLoader, final AsmSClass superclass) {
      this.classLoader = classLoader;
      classStartOffset = superclass == null ? 0 : superclass.subclassOffset;
   }

   void initialiseFieldMaps(final ClassNode classNode) {
      final List<?> fields = fields(classNode);
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
      subclassOffset = classStartOffset + declaredFieldMap.size();
   }

   private List fields(final ClassNode classNode) {
      if(classNode.name.equals(JavaConstants.CLASS_CLASS)) {
         final List<Object> result = new ArrayList<>(classNode.fields);
         result.add(new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL, AsmSClass.internalClassPointer.getName(), Type.getDescriptor(Object.class), "Ljava/lang/Object;", null));
         return result;
      }
      return classNode.fields;
   }
}