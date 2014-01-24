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

   Vop createInvokeSpecial(SMethodDescriptor sMethodName, InstructionSink instructionSink);

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
   Vop invokeInterface(String klassName, String methodName, String desc, InstructionSink sink);
   Vop aload(int index, InstructionSink sink);
   Vop fload(int index, InstructionSink sink);
   Vop dload(int index, InstructionSink sink);

   Vop nop(InstructionSink sink);

   Vop loadArg(Object object, InstructionSink sink);

   Vop newObject(String klassDesc, InstructionSink sink);

   Vop aconst_null(InstructionSink sink);

   Vop iconst_0(InstructionSink sink);

   Vop return1(InstructionSink sink);
   Vop return2(InstructionSink sink);
   Vop returnVoid(InstructionSink sink);
}