package com.lexicalscope.symb.vm.classloader.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.objectweb.asm.tree.FieldNode;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SField;
import com.lexicalscope.symb.vm.classloader.SFieldName;

public class AsmSClassBuilder {
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

   void initialiseFieldMaps(final List<FieldNode> fields) {
      for (final FieldNode fieldNode : fields) {
         final SField field = new SField(new SFieldName(name, fieldNode.name), fieldNode);

         withField(field);
      }
   }

   int staticOffset = 0;
   int dynamicOffset = 0;
   private void withField(final SField field) {
      if (field.isStatic()) {
         declaredStaticFieldMap.put(field.name(), staticOffset);
         staticOffset++;
      } else {
         declaredFields.add(field);
         declaredFieldMap.put(field.name(), dynamicOffset + classStartOffset);
         declaredFieldInit.add(classLoader.init(field.desc()));
         dynamicOffset++;
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