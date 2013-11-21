package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.tree.InsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.instructions.ops.IAddOp;

public class Iadd implements Instruction {
   private final InsnNode abstractInsnNode;

   public Iadd(final InsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      return state.advance(instructionFor(abstractInsnNode.getNext()), new IAddOp());
   }
}
