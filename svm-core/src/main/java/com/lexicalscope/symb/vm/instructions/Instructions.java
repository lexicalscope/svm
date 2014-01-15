package com.lexicalscope.symb.vm.instructions;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.classloader.SMethodDescriptor;

public interface Instructions {
   public interface InstructionSink {
      void nextInstruction(AbstractInsnNode asmInstruction, Instruction node);
      void noInstruction(AbstractInsnNode abstractInsnNode);
   }

   void instructionFor(AbstractInsnNode abstractInsnNode, InstructionSink instructionSink);

   Object initialFieldValue(String desc);

   Instruction defineClass(List<String> klassNames);

   Instruction createInvokeSpecial(SMethodDescriptor sMethodName);

   Instruction initThread();

   StatementBuilder statements();

   // TODO[tim]: the native method implementations should be split out
   Instruction currentThread();
   Instruction arrayCopy();
   Instruction floatToRawIntBits();
   Instruction doubleToRawLongBits();
   Instruction getCallerClass();
   Instruction getPrimitiveClass();
   Instruction returnVoid();
   Instruction return1();
   Instruction return2();
   Instruction newObject(String klassDesc);
   Instruction addressToHashCode();
   Instruction nanoTime();
   Instruction currentTimeMillis();
   Instruction aconst_null();
   Instruction iconst_0();
   Instruction fconst_0();
   Instruction iconst(int constVal);
   Instruction lconst(long constVal);
   Instruction invokeInterface(String klassName, String methodName, String desc);
   Instruction aload(int index);
   Instruction fload(int index);
   Instruction dload(int index);

   Instruction nop();

   Instruction loadArg(Object object);
}