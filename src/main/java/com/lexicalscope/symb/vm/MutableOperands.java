package com.lexicalscope.symb.vm;

import java.util.Deque;
import java.util.List;

public class MutableOperands {
   private final Deque<Object> operands;
   private final List<Object> locals;

   public MutableOperands(final List<Object> locals, final Deque<Object> operands) {
      this.locals = locals;
      this.operands = operands;
   }

   public Object pop() {
      return operands.pop();
   }

   public void push(final Object val) {
      operands.push(val);
   }

   public Object local(final int var) {
      return locals.get(var);
   }
}
