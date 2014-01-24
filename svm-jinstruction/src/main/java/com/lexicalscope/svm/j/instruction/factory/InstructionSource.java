package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.svm.j.instruction.factory.Instructions.InstructionSink;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface InstructionSource {
   Vop load(int var, InstructionSink sink);
   Vop store(int var, InstructionSink sink);
   Vop load2(int var, InstructionSink sink);
   Vop store2(int var, InstructionSink sink);

   Vop putField(FieldInsnNode fieldInsnNode, InstructionSink sink);
   Vop getField(FieldInsnNode fieldInsnNode, InstructionSink sink);
   Vop getStaticField(FieldInsnNode fieldInsnNode, InstructionSink sink);
   Vop putStaticField(FieldInsnNode fieldInsnNode, InstructionSink sink);

   Vop aconst_null(InstructionSink sink);

   Vop returnVoid(InstructionSink sink);
   Vop return1(InstructionSink sink);
   Vop return2(InstructionSink sink);

   Vop iand(InstructionSink sink);
   Vop iadd(InstructionSink sink);
   Vop imul(InstructionSink sink);
   Vop isub(InstructionSink sink);
   Vop ineg(InstructionSink sink);
   Vop ishl(InstructionSink sink);
   Vop ishr(InstructionSink sink);
   Vop iushr(InstructionSink sink);
   Vop ior(InstructionSink sink);
   Vop ixor(InstructionSink sink);
   Vop lushr(InstructionSink sink);
   Vop iinc(IincInsnNode iincInsnNode, InstructionSink sink);
   Vop i2l(InstructionSink sink);
   Vop i2f(InstructionSink sink);
   Vop iconst_m1(InstructionSink sink);
   Vop iconst_0(InstructionSink sink);
   Vop iconst_1(InstructionSink sink);
   Vop iconst_2(InstructionSink sink);
   Vop iconst_3(InstructionSink sink);
   Vop iconst_4(InstructionSink sink);
   Vop iconst_5(InstructionSink sink);
   Vop ifge(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ifgt(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ifle(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop iflt(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ifeq(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ifne(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ificmpeq(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ificmpne(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ificmple(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ificmplt(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ificmpgt(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ificmpge(JumpInsnNode jumpInsnNode, InstructionSink sink);

   Vop fmul(InstructionSink sink);
   Vop fdiv(InstructionSink sink);
   Vop f2i(InstructionSink sink);
   Vop fcmpg(InstructionSink sink);
   Vop fcmpl(InstructionSink sink);
   Vop fconst_0(InstructionSink sink);

   Vop land(InstructionSink sink);
   Vop lconst_0(InstructionSink sink);
   Vop lconst_1(InstructionSink sink);
   Vop l2i(InstructionSink sink);
   Vop lcmp(InstructionSink sink);

   Vop sipush(int val, InstructionSink sink);
   Vop bipush(int val, InstructionSink sink);

   Vop dup(InstructionSink sink);
   Vop dup_x1(InstructionSink sink);
   Vop pop(InstructionSink sink);

   Vop newarray(int val, InstructionSink sink);
   Vop anewarray(InstructionSink sink);
   Vop caStore(InstructionSink sink);
   Vop iaStore(InstructionSink sink);
   Vop aaStore(InstructionSink sink);
   Vop caload(InstructionSink sink);
   Vop iaload(InstructionSink sink);
   Vop aaload(InstructionSink sink);
   Vop arrayLength(InstructionSink sink);

   Vop ldcInt(int cst, InstructionSink sink);
   Vop ldcLong(long cst, InstructionSink sink);
   Vop ldcFloat(float cst, InstructionSink sink);
   Vop ldcDouble(double cst, InstructionSink sink);
   Vop stringPoolLoad(String cst, InstructionSink sink);
   Vop objectPoolLoad(Type toLoad, InstructionSink sink);

   Vop newObject(String desc, InstructionSink sink);
   Vop instance0f(TypeInsnNode typeInsnNode, InstructionSink sink);
   Vop checkcast(TypeInsnNode typeInsnNode, InstructionSink sink);
   Vop ifnull(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ifnonnull(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop ifacmpne(JumpInsnNode jumpInsnNode, InstructionSink sink);
   Vop invokestatic(SMethodDescriptor name, InstructionSink sink);
   Vop invokespecial(SMethodDescriptor name, InstructionSink sink);
   Vop invokeinterface(SMethodDescriptor name, InstructionSink sink);
   Vop invokevirtual(SMethodDescriptor name, InstructionSink sink);

   Vop got0(InstructionSink sink);
}
