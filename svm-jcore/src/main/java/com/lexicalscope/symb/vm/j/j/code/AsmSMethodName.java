package com.lexicalscope.symb.vm.j.j.code;

import static com.lexicalscope.symb.vm.j.JavaConstants.*;
import static org.objectweb.asm.Type.*;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.vm.j.SVirtualMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public final class AsmSMethodName implements Comparable<AsmSMethodName>, SMethodDescriptor {
   private final String klassName;
   private final SVirtualMethodName virtualName;
   private final int hashCode;

   public AsmSMethodName(
         final String klassName,
         final String name,
         final String desc
         ) {
      this.klassName = klassName;
      this.virtualName = new AsmSVirtualMethodName(name, desc);
      this.hashCode = klassName.hashCode() ^ virtualName.hashCode();
   }

   public AsmSMethodName(
         final Class<?> klass,
         final String name,
         final String desc
         ) {
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
   public int returnCount() {
      return getArgumentsAndReturnSizes(desc()) & 0x03;
   }

   @Override
   public SVirtualMethodName virtualName() {
      return virtualName;
   }

   public static SMethodDescriptor staticInitialiser(final String klassName) {
      return new AsmSMethodName(klassName, CLINIT, NOARGS_VOID_DESC);
   }

   public static SMethodDescriptor defaultConstructor(final String klassName) {
      return new AsmSMethodName(klassName, INIT, NOARGS_VOID_DESC);
   }

   public static SMethodDescriptor defaultConstructor(final Class<?> klass) {
      return defaultConstructor(getInternalName(klass));
   }
}
