package com.lexicalscope.symb.code;

import static org.objectweb.asm.Type.getArgumentsAndReturnSizes;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.klass.SMethodDescriptor;
import com.lexicalscope.symb.vm.SVirtualMethodName;

public final class AsmSMethodName implements Comparable<AsmSMethodName>, SMethodDescriptor {
   private final String klassName;
   private final SVirtualMethodName virtualName;
   private final int hashCode;

   public AsmSMethodName(final String klassName, final String name, final String desc) {
      this.klassName = klassName;
      this.virtualName = new AsmSVirtualMethodName(name, desc);
      this.hashCode = klassName.hashCode() ^ virtualName.hashCode();
   }

   public AsmSMethodName(final Class<?> klass, final String name, final String desc) {
      this(Type.getInternalName(klass), name, desc);
   }

   @Override
   public boolean isVoidMethod() {
      return Type.getReturnType(virtualName.desc()).equals(Type.VOID_TYPE);
   }

   @Override
   public int compareTo(final AsmSMethodName o) {
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
         final AsmSMethodName that = (AsmSMethodName) obj;
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

   @Override
   public String klassName() {
      return klassName;
   }

   @Override
   public String name() {
      return virtualName.name();
   }

   @Override
   public String desc() {
      return virtualName.desc();
   }

   @Override
   public int argSize() {
      return getArgumentsAndReturnSizes(desc()) >> 2;
   }

   @Override
   public SVirtualMethodName virtualName() {
      return virtualName;
   }
}
