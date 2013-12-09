package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.classloader.SClassLoader;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(AbstractInsnNode asmInstruction, Instruction node);
      void noInstruction(AbstractInsnNode abstractInsnNode);
   }

   void instructionFor(SClassLoader classLoader, AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);
}