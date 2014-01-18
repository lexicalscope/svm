package com.lexicalscope.symb.klass;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DeclaredFields {
   private final List<SField> declaredFields = new ArrayList<>();
   private final Map<SFieldName, Integer> staticFieldMap = new LinkedHashMap<>();

   public void addDynamic(final SField field) {
      declaredFields.add(field);
   }

   public void addStatic(final SField field) {
      staticFieldMap.put(field.name(), staticFieldMap.size());
   }

   public int dynamicCount() {
      return declaredFields.size();
   }

   public List<SField> dynamicFields() {
      return declaredFields;
   }

   public int staticCount() {
      return staticFieldMap.size();
   }

   public String staticFieldsToString() {
      return staticFieldMap.toString();
   }

   public List<Object> fieldInit() {
      final List<Object> fieldInit = new ArrayList<>();
      for (final SField field : declaredFields) {
         fieldInit.add(field.init());
      }
      return fieldInit;
   }

   public Integer staticFieldIndex(final SFieldName name) {
      return staticFieldMap.get(name);
   }

   public boolean containsStaticField(final SFieldName name) {
      return staticFieldMap.containsKey(name);
   }
}
