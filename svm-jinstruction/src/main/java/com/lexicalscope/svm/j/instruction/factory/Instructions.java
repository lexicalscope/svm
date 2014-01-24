package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface Instructions {
   public interface InstructionSink {
      void linearInstruction(Vop node);
      void nextInstruction(Vop node);
      void noInstruction();
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Object initialFieldValue(String desc);

   Vop defineClass(List<String> klassNames);

   void classDefaultConstructor(String klassName, InstructionSink sink);

   void initThread(InstructionSink sink);

   StatementBuilder statements();

   // TODO[tim]: the native method implementations should be split out
   void currentThread(InstructionSink sink);
   void arrayCopy(InstructionSink sink);
   void floatToRawIntBits(InstructionSink sink);
   void doubleToRawLongBits(InstructionSink sink);
   void getCallerClass(InstructionSink sink);
   void getPrimitiveClass(InstructionSink sink);
   void addressToHashCode(InstructionSink sink);
   void nanoTime(InstructionSink sink);
   void currentTimeMillis(InstructionSink sink);

   void iconst(int constVal, InstructionSink sink);
   void lconst(long constVal, InstructionSink sink);
   void invokeInterface(SMethodDescriptor sMethodName, InstructionSink sink);

   void nop(InstructionSink sink);

   void loadArg(Object object, InstructionSink sink);

   InstructionSource source();
}