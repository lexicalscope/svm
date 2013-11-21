package com.lexicalscope.symb.vm;

import java.util.Deque;
import java.util.List;

public class MutableStackFrame {
   private final List<Object> localsCopy;
   private final Deque<Object> operandsCopy;

   public MutableStackFrame(final List<Object> localsCopy, final Deque<Object> operandsCopy) {
      this.localsCopy = localsCopy;
      this.operandsCopy = operandsCopy;
   }

   public Object local(final int index) {
      return localsCopy.get(index);
   }

   public void push(final Object local) {
      operandsCopy.push(local);
   }
}
