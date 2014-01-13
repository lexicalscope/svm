package com.lexicalscope.symb.vm.classloader.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SField;
import com.lexicalscope.symb.vm.classloader.SFieldName;

class AsmSClassBuilder {
   private final SClassLoader classLoader;
   private final int classStartOffset;
   final List<SField> declaredFields = new ArrayList<>();
   final TreeMap<SFieldName, Integer> declaredFieldMap = new TreeMap<>();
   final TreeMap<SFieldName, Integer> declaredStaticFieldMap = new TreeMap<>();
   final List<Object> declaredFieldInit = new ArrayList<>();

   public AsmSClassBuilder(final SClassLoader classLoader, final AsmSClass superclass) {
      this.classLoader = classLoader;
      classStartOffset = superclass == null ? 0 : superclass.subclassOffset;
   }

   int staticOffset = 0;
   int dynamicOffset = 0;
   void initialiseFieldMaps(final List<FieldNode> fields) {
      for (final FieldNode fieldNode : fields) {
         final SFieldName fieldName = new SFieldName(name, fieldNode.name);
         final SField field = new SField(fieldName, fieldNode);

         if ((fieldNode.access & Opcodes.ACC_STATIC) != 0) {
            declaredStaticFieldMap.put(fieldName, staticOffset);
            staticOffset++;
         } else {
            declaredFields.add(field);
            declaredFieldMap.put(fieldName, dynamicOffset + classStartOffset);
            declaredFieldInit.add(classLoader.init(fieldNode.desc));
            dynamicOffset++;
         }
      }
   }

   public int subclassOffset() {
      return classStartOffset + declaredFieldMap.size();
   }

   private String name;
   public AsmSClassBuilder withName(final String name) {
      this.name = name;
      return this;
   }
}