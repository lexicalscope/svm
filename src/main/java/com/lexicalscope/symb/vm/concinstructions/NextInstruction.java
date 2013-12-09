package com.lexicalscope.symb.vm.concinstructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.classloader.SClassLoader;
import com.lexicalscope.symb.vm.instructions.ops.StackFrameVop;

public class NextInstruction implements Transistion {
   @Override
   public void next(final SClassLoader cl, final State state, final AbstractInsnNode abstractInsnNode, final Instruction instruction) {
      state.op(new StackFrameVop() {
         @Override
         public void eval(final StackFrame stackFrame) {
            stackFrame.advance(instruction.next());
         }
      });
   }
}
