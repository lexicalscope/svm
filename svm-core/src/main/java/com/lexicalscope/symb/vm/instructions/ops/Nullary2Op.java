package com.lexicalscope.symb.vm.instructions.ops;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class Nullary2Op implements Vop {
   private final Nullary2Operator operator;

   public Nullary2Op(final Nullary2Operator operator) {
      this.operator = operator;
   }

   @Override
   public String toString() {
      return operator.toString();
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      stackFrame.pushDoubleWord(operator.eval());
   }
}
