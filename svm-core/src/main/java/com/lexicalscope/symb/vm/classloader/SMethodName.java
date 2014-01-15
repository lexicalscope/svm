package com.lexicalscope.symb.vm.classloader;

import static org.objectweb.asm.Type.getArgumentsAndReturnSizes;

import org.objectweb.asm.Type;

public final class SMethodName implements Comparable<SMethodName> {
   private final String klassName;
   private final SVirtualMethodName virtualName;
   private final int hashCode;

   public SMethodName(final String klassName, final String name, final String desc) {
      this.klassName = klassName;
      this.virtualName = new SVirtualMethodName(name, desc);
      this.hashCode = klassName.hashCode() ^ virtualName.hashCode();
   }

   public SMethodName(final Class<?> klass, final String name, final String desc) {
      this(Type.getInternalName(klass), name, desc);
   }

   public boolean isVoidMethod() {
      return Type.getReturnType(virtualName.desc()).equals(Type.VOID_TYPE);
   }

   @Override
   public int compareTo(final SMethodName o) {
      final int firstCompare = this.klassName.compareTo(o.klassName);
      if(firstCompare != 0) {
         return firstCompare;
      }
      return this.virtualName.compareTo(o.virtualName);
   }

   @Override
   public boolean equals(final Object obj) {
      if (obj == this) {
         return true;
      }

      if(obj != null && obj.getClass().equals(this.getClass())) {
         final SMethodName that = (SMethodName) obj;
         return that.klassName.equals(klassName) && that.virtualName.equals(virtualName);
      }
      return false;
   }

   @Override
   public int hashCode() {
      return hashCode;
   }

   @Override
   public String toString() {
      return klassName + "." + virtualName;
   }

   public String klassName() {
      return klassName;
   }

   public String name() {
      return virtualName.name();
   }

   public String desc() {
      return virtualName.desc();
   }

   public int argSize() {
      return getArgumentsAndReturnSizes(desc()) >> 2;
   }

   public SVirtualMethodName virtualName() {
      return virtualName;
   }
}
