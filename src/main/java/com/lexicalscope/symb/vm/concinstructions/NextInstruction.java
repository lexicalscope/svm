package com.lexicalscope.symb.vm.concinstructions;

import static com.lexicalscope.symb.vm.concinstructions.Instructions.instructionFor;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public class NextInstruction implements Transistion {
   @Override
   public void next(final Vm vm, final State state, final AbstractInsnNode abstractInsnNode) {
      state.op(new StackFrameVop() {
         @Override
         public void eval(final StackFrame stackFrame) {
            stackFrame.advance(instructionFor(abstractInsnNode.getNext()));
         }
      });
   }
}
