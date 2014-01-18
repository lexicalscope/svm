package com.lexicalscope.symb.vm;


public class VopAdapter implements Vop {
   private final Op<?> op;

   public VopAdapter(final Op<?> op) {
      this.op = op;
   }

   @Override public void eval(final State ctx) {
      op.eval(ctx);
   }

   @Override public String toString() {
      return op.toString();
   }
}
