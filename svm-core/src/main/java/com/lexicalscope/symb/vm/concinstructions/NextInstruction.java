package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.InstructionNode;

public class NextInstruction implements Transistion {
   @Override
   public void next(final State state, final InstructionNode instruction) {
      state.op(new Vop() {
         @Override public void eval(Vm vm, Statics statics, final Heap heap, Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
            stackFrame.advance(instructionNode.next());
         }
      }, null);
   }
}
