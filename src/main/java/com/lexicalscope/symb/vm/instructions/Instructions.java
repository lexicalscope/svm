package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(AbstractInsnNode asmInstruction, Instruction node);
      void noInstruction(AbstractInsnNode abstractInsnNode);
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Instruction defineClass(String internalName);
   Instruction createInvokeSpecial(SMethodName sMethodName);

   Instruction initThread();

   StatementBuilder statements();
}