package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.Vop;

public interface Instructions {
   public interface InstructionSink {
      void linearInstruction(Vop node);
      void loadingInstruction(List<String> classes, Vop op, Instructions factory);
      void nextInstruction(Vop node);
      void noInstruction();
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Object initialFieldValue(String desc);

   Vop defineClass(List<String> klassNames);

   void classDefaultConstructor(String klassName, InstructionSink sink);

   StatementBuilder statements();
   StatementBuilder before(Instruction nextInstruction);

   void loadArg(Object object, InstructionSink sink);

   InstructionSource source();

}