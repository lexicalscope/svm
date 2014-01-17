package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.vm.StateImpl;
import com.lexicalscope.symb.vm.Vop;

public class ObjectPoolLoad implements Vop {
   private final String internalName;

   public ObjectPoolLoad(final Type type) {
      this(type.getInternalName());
   }

   public ObjectPoolLoad(final String internalName) {
      this.internalName = internalName;
   }

   @Override public void eval(final StateImpl ctx) {
      ctx.push(ctx.whereMyClassAt(internalName));
   }

   @Override public String toString() {
      return "LDC object " + internalName;
   }
}
