package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.Instructions.instructionFor;

import org.objectweb.asm.tree.InsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vm;

public class Iadd implements Instruction {
   private final InsnNode abstractInsnNode;

   public Iadd(final InsnNode abstractInsnNode) {
      this.abstractInsnNode = abstractInsnNode;
   }

   @Override
   public State eval(final Vm vm, final State state) {
      final int op0 = (int) state.peekOperand();
      final State result = state.popOperand();
      final int op1 = (int) result.peekOperand();
      return result.advance(instructionFor(abstractInsnNode.getNext())).popOperand().loadConst(op0 + op1);
   }
}
