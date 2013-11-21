package com.lexicalscope.symb.vm;

import java.util.Deque;

public class MutableOperands {
   private final Deque<Object> operands;

   public MutableOperands(final Deque<Object> operands) {
      this.operands = operands;
   }

   public Object pop() {
      return operands.pop();
   }

   public void push(final Object val) {
      operands.push(val);
   }
}
