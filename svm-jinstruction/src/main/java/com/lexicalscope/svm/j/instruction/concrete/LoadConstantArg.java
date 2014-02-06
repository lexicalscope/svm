package com.lexicalscope.svm.j.instruction.concrete;

import com.lexicalscope.symb.vm.j.InstructionQuery;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;

public final class LoadConstantArg implements Vop {
   private final Object value;

   public LoadConstantArg(final Object value) {
      //TODO[tim]: could assert value instanceof Integer || value instanceof ISymbol : value.getClass();
      this.value = value;
   }

   @Override public void eval(final State ctx) {
      ctx.push(value);
   }

   @Override public <S> S query(final InstructionQuery<S> instructionQuery) {
      return instructionQuery.loadarg(value);
   }
}