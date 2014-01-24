package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(Vop node);
      void noInstruction();
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Object initialFieldValue(String desc);

   Vop defineClass(List<String> klassNames);

   void createInvokeSpecial(SMethodDescriptor sMethodName, InstructionSink instructionSink);

   Vop initThread(InstructionSink sink);

   StatementBuilder statements();

   // TODO[tim]: the native method implementations should be split out
   Vop currentThread(InstructionSink sink);
   Vop arrayCopy(InstructionSink sink);
   Vop floatToRawIntBits(InstructionSink sink);
   Vop doubleToRawLongBits(InstructionSink sink);
   Vop getCallerClass(InstructionSink sink);
   Vop getPrimitiveClass(InstructionSink sink);
   Vop addressToHashCode(InstructionSink sink);
   Vop nanoTime(InstructionSink sink);
   Vop currentTimeMillis(InstructionSink sink);
   Vop iconst(int constVal, InstructionSink sink);
   Vop lconst(long constVal, InstructionSink sink);
   void invokeInterface(String klassName, String methodName, String desc, InstructionSink sink);
   void aload(int index, InstructionSink sink);
   void fload(int index, InstructionSink sink);
   void dload(int index, InstructionSink sink);

   Vop nop(InstructionSink sink);

   Vop loadArg(Object object, InstructionSink sink);

   void newObject(String klassDesc, InstructionSink sink);

   void aconst_null(InstructionSink sink);

   void iconst_0(InstructionSink sink);

   void return1(InstructionSink sink);
   void return2(InstructionSink sink);
   void returnVoid(InstructionSink sink);
}