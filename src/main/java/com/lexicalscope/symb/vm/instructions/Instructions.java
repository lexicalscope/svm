package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(AbstractInsnNode asmInstruction, InstructionNode node);
   }

   void instructionFor(SClassLoader classLoader, AbstractInsnNode abstractInsnNode, InstructionNode previous, InstructionSink instructionSink);
}