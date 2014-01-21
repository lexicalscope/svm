package com.lexicalscope.symb.vm.conc;

public class MethodInfo {
   private final String klass;
   private final String name;
   private final String desc;

   public MethodInfo(final String klass, final String name, final String desc) {
      this.klass = klass;
      this.name = name;
      this.desc = desc;
   }

   public MethodInfo(final Class<?> klass, final String name, final String desc) {
      this(klass.getName().replace(".", "/"), name, desc);
   }

   public String klass() {
      return klass;
   }

   public String name() {
      return name;
   }

   public String desc() {
      return desc;
   }
}
