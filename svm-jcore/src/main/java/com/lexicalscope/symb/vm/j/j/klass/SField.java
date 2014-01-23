package com.lexicalscope.symb.vm.j.j.klass;


public final class SField {
   private final SFieldName fieldName;
   private final FieldDesc fieldDesc;
   private final Object init;

   public SField(final SFieldName fieldName, final FieldDesc fieldDesc, final Object init) {
      this.fieldName = fieldName;
      this.fieldDesc = fieldDesc;
      this.init = init;
   }

   public String desc() {
	   // TODO[tim] don't use raw field desc
      return fieldDesc.desc();
   }

   public boolean isStatic() {
	   return fieldDesc.isStatic();
   }

   public SFieldName name() {
      return fieldName;
   }

   public Object init() {
      return init;
   }

   @Override public String toString() {
      return fieldName.toString();
   }
}
