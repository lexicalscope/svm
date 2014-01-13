package com.lexicalscope.symb.vm.classloader.asm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lexicalscope.symb.vm.classloader.SField;
import com.lexicalscope.symb.vm.classloader.SFieldName;

class DeclaredFields {
   private final List<SField> declaredFields = new ArrayList<>();
   private final Map<SFieldName, Integer> declaredFieldMap = new LinkedHashMap<>();
   private final int classStartOffset;

   public DeclaredFields(final int classStartOffset) {
      this.classStartOffset = classStartOffset;
   }

   public void addField(final SField field) {
      declaredFields.add(field);
      declaredFieldMap.put(field.name(), classStartOffset + count());
   }

   public int count() {
      return declaredFieldMap.size();
   }

   public Map<SFieldName, Integer> map() {
      return declaredFieldMap;
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
