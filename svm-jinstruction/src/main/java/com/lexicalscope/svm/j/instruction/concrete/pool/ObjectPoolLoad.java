package com.lexicalscope.svm.j.instruction.concrete.pool;

import org.objectweb.asm.Type;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;

public class ObjectPoolLoad implements Vop {
   private final String internalName;

   public ObjectPoolLoad(final Type type) {
      this(type.getInternalName());
   }

   public ObjectPoolLoad(final String internalName) {
      this.internalName = internalName;
   }

   @Override public void eval(final JState ctx) {
      ctx.push(ctx.whereMyClassAt(internalName));
   }

   @Override public String toString() {
      return "LDC object " + internalName;
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.objectpoolload();
   }
}
