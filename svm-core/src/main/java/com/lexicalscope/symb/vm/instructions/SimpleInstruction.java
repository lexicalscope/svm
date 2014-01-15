package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class SimpleInstruction implements Instruction {
   private final Vop op;

   public SimpleInstruction(final Vop op) {
      this.op = op;
   }

   public SimpleInstruction(final Op<?> op) {
      this(new Vop() {
         @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
            op.eval(stackFrame, stack, heap, statics);
         }

         @Override public String toString() {
            return op.toString();
         }
      });
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      state.op(op);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
