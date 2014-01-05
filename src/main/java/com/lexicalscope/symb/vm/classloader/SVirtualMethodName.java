package com.lexicalscope.symb.vm.classloader;

import org.objectweb.asm.Type;

public class SVirtualMethodName implements Comparable<SVirtualMethodName> {
   private final String desc;
   private final String name;

   public SVirtualMethodName(final String name, final String desc) {
      this.name = name;
      this.desc = desc;
   }

   public boolean isVoidMethod() {
      return Type.getReturnType(desc).equals(Type.VOID_TYPE);
   }

   @Override
   public int compareTo(final SVirtualMethodName o) {
      final int firstCompare = this.name.compareTo(o.name);
      if(firstCompare != 0) {
         return firstCompare;
      }
      return this.desc.compareTo(o.desc);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj == this) {
         return true;
      }

      if(obj != null && obj.getClass().equals(this.getClass())) {
         final SVirtualMethodName that = (SVirtualMethodName) obj;
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
      return name + desc;
   }

   public String name() {
      return name;
   }

   public String desc() {
      return desc;
   }
}