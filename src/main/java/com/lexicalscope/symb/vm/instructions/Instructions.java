package com.lexicalscope.symb.vm.instructions;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.classloader.SMethodName;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(AbstractInsnNode asmInstruction, Instruction node);
      void noInstruction(AbstractInsnNode abstractInsnNode);
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Object initialFieldValue(String desc);

   Instruction defineClass(List<String> klassNames);

   Instruction createInvokeSpecial(SMethodName sMethodName);

   Instruction initThread();

   StatementBuilder statements();
}