package com.lexicalscope.symb.vm.instructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

// TODO[tim]: this does nothing
public class SimpleInstruction implements Instruction {
   private final Vop op;

   public SimpleInstruction(final Vop op) {
      this.op = op;
   }

   public SimpleInstruction(final Op<?> op) {
      this(new Vop() {
         @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
            op.eval(null, statics, heap, stack, stackFrame);
         }

         @Override public String toString() {
            return op.toString();
         }
      });
   }

   @Override public void eval(final Vm<State> vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, final InstructionNode instructionNode) {
      op.eval(vm, statics, heap, stack, stackFrame, instructionNode);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
