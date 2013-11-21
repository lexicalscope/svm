package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class NextInstruction implements Transistion {
   @Override public State next(final Vm vm, final State state, final AbstractInsnNode abstractInsnNode) {
      return state.advance(instructionFor(abstractInsnNode.getNext()));
   }
}
