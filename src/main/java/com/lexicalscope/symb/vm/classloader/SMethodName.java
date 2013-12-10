package com.lexicalscope.symb.vm.classloader;

public class SMethodName implements Comparable<SMethodName> {
   private final String desc;
   private final String name;

   public SMethodName(final String name, final String desc) {
      this.name = name;
      this.desc = desc;
   }

   @Override
   public int compareTo(final SMethodName o) {
      final int firstCompare = this.name.compareTo(o.name);
      if(firstCompare == 0) {
         return this.desc.compareTo(o.desc);
      }
      return firstCompare;
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
      return name + desc;
   }
}
