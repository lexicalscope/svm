package com.lexicalscope.symb.vm.classloader.asm;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.symb.vm.classloader.AsmSMethod;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.classloader.SField;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.Instructions;

public class AsmSClassBuilder {
   private String klassName;
   private final SClassLoader classLoader;
   private final DeclaredFields declaredFields = new DeclaredFields();
   private final DeclaredMethods declaredMethods = new DeclaredMethods();
   private final Instructions instructions;

   public AsmSClassBuilder(
         final SClassLoader classLoader,
         final Instructions instructions,
         final AsmSClass superclass) {
      this.classLoader = classLoader;
      this.instructions = instructions;
   }

   AsmSClassBuilder withFields(final List<FieldNode> fields) {
      for (final FieldNode fieldNode : fields) {
         withField(new SField(new SFieldName(klassName, fieldNode.name), fieldNode, classLoader.init(fieldNode.desc)));
      }
      return this;
   }

   private void withField(final SField field) {
      if (field.isStatic()) {
         declaredFields.addStatic(field);
      } else {
         declaredFields.addDynamic(field);
      }
   }

   public AsmSClassBuilder withMethods(final List<MethodNode> methods) {
      for (final MethodNode method : methods) {
         declaredMethods.add(new AsmSMethod(classLoader, new SMethodName(klassName, method.name, method.desc), instructions, method));
      }
      return this;
   }

   public AsmSClassBuilder withName(final String name) {
      this.klassName = name;
      return this;
   }

   public DeclaredFields declaredFields() {
      return declaredFields;
   }

   public DeclaredMethods declaredMethods() {
      return declaredMethods;
   }
}