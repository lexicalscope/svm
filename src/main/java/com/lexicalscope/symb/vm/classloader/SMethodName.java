package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Type;

public final class SMethodName implements Comparable<SMethodName> {
   private final String klassName;
   private final String desc;
   private final String name;

   public SMethodName(final String klassName, final String name, final String desc) {
      this.klassName = klassName;
      this.name = name;
      this.desc = desc;
   }

   public SMethodName(final Class<?> klass, final String name, final String desc) {
      this(Type.getInternalName(klass), name, desc);
   }

   public boolean isVoidMethod() {
      return Type.getReturnType(desc).equals(Type.VOID_TYPE);
   }

   @Override
   public int compareTo(final SMethodName o) {
      final int firstCompare = this.klassName.compareTo(o.klassName);
      if(firstCompare != 0) {
         return firstCompare;
      }
      final int secondCompare = this.name.compareTo(o.name);
      if(secondCompare != 0) {
         return secondCompare;
      }
      return this.desc.compareTo(o.desc);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj == this) return true;

      if(obj != null && obj.getClass().equals(this.getClass())) {
         final SMethodName that = (SMethodName) obj;
         return that.desc.equals(desc) && that.name.equals(name);
      }
      return false;
   }

   @Override
   public int hashCode() {
      return desc.hashCode() ^ name.hashCode();
   }

   @Override
   public String toString() {
      return klassName + "." + name + desc;
   }

   public String klassName() {
      return klassName;
   }

   public String name() {
      return name;
   }

   public String desc() {
      return desc;
   }
}
