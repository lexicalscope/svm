package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(AbstractInsnNode asmInstruction, Vop node);
      void noInstruction(AbstractInsnNode abstractInsnNode);
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Object initialFieldValue(String desc);

   Vop defineClass(List<String> klassNames);

   Vop createInvokeSpecial(SMethodDescriptor sMethodName);

   Vop initThread();

   StatementBuilder statements();

   // TODO[tim]: the native method implementations should be split out
   Vop currentThread();
   Vop arrayCopy();
   Vop floatToRawIntBits();
   Vop doubleToRawLongBits();
   Vop getCallerClass();
   Vop getPrimitiveClass();
   Vop returnVoid();
   Vop return1();
   Vop return2();
   Vop newObject(String klassDesc);
   Vop addressToHashCode();
   Vop nanoTime();
   Vop currentTimeMillis();
   Vop aconst_null();
   Vop iconst_0();
   Vop fconst_0();
   Vop iconst(int constVal);
   Vop lconst(long constVal);
   Vop invokeInterface(String klassName, String methodName, String desc);
   Vop aload(int index);
   Vop fload(int index);
   Vop dload(int index);

   Vop nop();

   Vop loadArg(Object object);
}