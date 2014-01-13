package com.lexicalscope.symb.vm.classloader.asm;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.symb.vm.classloader.SField;

class DeclaredFields {
   private final List<SField> declaredFields = new ArrayList<>();

   public void addField(final SField field) {
      declaredFields.add(field);
   }

   public int count() {
      return declaredFields.size();
   }

   public List<SField> fields() {
      return declaredFields;
   }

   public List<Object> fieldInit() {
      final List<Object> fieldInit = new ArrayList<>();
      for (final SField field : declaredFields) {
         fieldInit.add(field.init());
      }
      return fieldInit;
   }
}
