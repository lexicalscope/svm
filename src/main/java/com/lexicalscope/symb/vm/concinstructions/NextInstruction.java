package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StackFrame;

public class NextInstruction implements Transistion {
   @Override
   public void next(final State state, final InstructionNode instruction) {
      state.op(new Vop() {
         @Override public void eval(final StackFrame stackFrame, final Heap heap) {
            stackFrame.advance(instruction.next());
         }
      });
   }
}
