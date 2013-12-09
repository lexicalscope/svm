package com.lexicalscope.symb.vm.concinstructions;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public class NextInstruction implements Transistion {
   @Override
   public void next(final State state, final InstructionNode instruction) {
      state.op(new StackFrameVop() {
         @Override
         public void eval(final StackFrame stackFrame) {
            stackFrame.advance(instruction.next());
         }
      });
   }
}
