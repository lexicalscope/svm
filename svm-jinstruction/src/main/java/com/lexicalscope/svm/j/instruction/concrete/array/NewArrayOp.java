package com.lexicalscope.svm.j.instruction.concrete.array;

import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.Vop;

public class NewArrayOp implements Vop {
   public static final int ARRAY_CLASS_OFFSET = 0;
   public static final int ARRAY_LENGTH_OFFSET = 1;
   public static final int ARRAY_PREAMBLE = 2;
   private final InitStrategy initStrategy;
   private final ArrayConstructor arrayConstructor;

   public NewArrayOp(final ArrayConstructor arrayConstructor) {
      this(new ReferenceInitStrategy(), arrayConstructor);
   }

   public NewArrayOp(final Object initialValue, final ArrayConstructor arrayConstructor) {
      this(new PrimitiveInitStrategy(initialValue), arrayConstructor);
   }

   private NewArrayOp(final InitStrategy initStrategy, final ArrayConstructor arrayConstructor) {
      this.initStrategy = initStrategy;
      this.arrayConstructor = arrayConstructor;
   }

   @Override public void eval(final State ctx) {
      arrayConstructor.newArray(ctx, initStrategy);
   }

   @Override public String toString() {
      return "NEWARRAY";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.newarray();
   }
}
