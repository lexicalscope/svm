package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.HeapVop;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;

public class NextInstruction implements Transistion {
   @Override
   public void next(final State state, final InstructionNode instruction) {
      state.op(new HeapVop() {
         @Override public void eval(final StackFrame stackFrame, final Heap heap) {
            stackFrame.advance(instruction.next());
         }
      });
   }
}
