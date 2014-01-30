package com.lexicalscope.symb.classloading.asm;

import java.util.List;

import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.classloading.AsmSMethod;
import com.lexicalscope.symb.classloading.SClassLoader;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.DeclaredFields;
import com.lexicalscope.symb.vm.j.j.klass.DeclaredMethods;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SField;
import com.lexicalscope.symb.vm.j.j.klass.SFieldName;

public class AsmSClassBuilder {
   private String klassName;
   private final SClassLoader classLoader;
   private final DeclaredFields declaredFields = new DeclaredFields();
   private final DeclaredMethods declaredMethods = new DeclaredMethods();
   private final Instructions instructions;

   public AsmSClassBuilder(
         final SClassLoader classLoader,
         final Instructions instructions,
         final SClass superclass) {
      this.classLoader = classLoader;
      this.instructions = instructions;
   }

   AsmSClassBuilder withFields(final List<FieldNode> fields) {
      for (final FieldNode fieldNode : fields) {
         withField(
               new SField(
                     new SFieldName(klassName, fieldNode.name),
                     new AsmFieldDesc(fieldNode),
                     instructions.source().initialFieldValue(fieldNode.desc)));
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
         declaredMethods.add(
               new AsmSMethod(
                     classLoader,
                     new AsmSMethodName(klassName, method.name, method.desc),
                     instructions,
                     method));
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