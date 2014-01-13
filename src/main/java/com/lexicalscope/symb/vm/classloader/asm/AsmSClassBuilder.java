package com.lexicalscope.symb.vm.classloader.asm;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;

import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SField;
import com.lexicalscope.symb.vm.classloader.SFieldName;

public class AsmSClassBuilder {
   private final SClassLoader classLoader;
   private final DeclaredFields declaredFields;

   public AsmSClassBuilder(final SClassLoader classLoader, final AsmSClass superclass) {
      this.classLoader = classLoader;
      declaredFields = new DeclaredFields();
   }

   void withFields(final List<FieldNode> fields) {
      for (final FieldNode fieldNode : fields) {
         withField(new SField(new SFieldName(name, fieldNode.name), fieldNode, classLoader.init(fieldNode.desc)));
      }
   }

   private void withField(final SField field) {
      if (field.isStatic()) {
         declaredFields.addStatic(field);
      } else {
         declaredFields.addDynamic(field);
      }
   }

   private String name;
   public AsmSClassBuilder withName(final String name) {
      this.name = name;
      return this;
   }

   public DeclaredFields declaredFields() {
      return declaredFields;
   }
}