package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface InstructionSource {
   Vop load(int var);
   Vop store(int var);
   Vop load2(int var);
   Vop store2(int var);

   Vop putField(FieldInsnNode fieldInsnNode);
   Vop getField(FieldInsnNode fieldInsnNode);
   Vop getStaticField(FieldInsnNode fieldInsnNode);
   Vop putStaticField(FieldInsnNode fieldInsnNode);

   Vop aconst_null();

   Vop returnVoid();
   Vop return1();
   Vop return2();

   Vop iand();
   Vop iadd();
   Vop imul();
   Vop isub();
   Vop ineg();
   Vop ishl();
   Vop ishr();
   Vop iushr();
   Vop ior();
   Vop ixor();
   Vop lushr();
   Vop iinc(IincInsnNode iincInsnNode);
   Vop i2l();
   Vop i2f();
   Vop iconst_m1();
   Vop iconst_0();
   Vop iconst_1();
   Vop iconst_2();
   Vop iconst_3();
   Vop iconst_4();
   Vop iconst_5();
   Vop ifge(JumpInsnNode jumpInsnNode);
   Vop ifgt(JumpInsnNode jumpInsnNode);
   Vop ifle(JumpInsnNode jumpInsnNode);
   Vop iflt(JumpInsnNode jumpInsnNode);
   Vop ifeq(JumpInsnNode jumpInsnNode);
   Vop ifne(JumpInsnNode jumpInsnNode);
   Vop ificmpeq(JumpInsnNode jumpInsnNode);
   Vop ificmpne(JumpInsnNode jumpInsnNode);
   Vop ificmple(JumpInsnNode jumpInsnNode);
   Vop ificmplt(JumpInsnNode jumpInsnNode);
   Vop ificmpgt(JumpInsnNode jumpInsnNode);
   Vop ificmpge(JumpInsnNode jumpInsnNode);

   Vop fmul();
   Vop fdiv();
   Vop f2i();
   Vop fcmpg();
   Vop fcmpl();
   Vop fconst_0();

   Vop land();
   Vop lconst_0();
   Vop lconst_1();
   Vop l2i();
   Vop lcmp();

   Vop sipush(int val);
   Vop bipush(int val);

   Vop dup();
   Vop dup_x1();
   Vop pop();

   Vop newarray(int val);
   Vop anewarray();
   Vop caStore();
   Vop iaStore();
   Vop aaStore();
   Vop caload();
   Vop iaload();
   Vop aaload();
   Vop arrayLength();

   Vop ldcInt(int cst);
   Vop ldcLong(long cst);
   Vop ldcFloat(float cst);
   Vop ldcDouble(double cst);
   Vop stringPoolLoad(String cst);
   Vop objectPoolLoad(Type toLoad);

   Vop newObject(String desc);
   Vop instance0f(TypeInsnNode typeInsnNode);
   Vop checkcast(TypeInsnNode typeInsnNode);
   Vop ifnull(JumpInsnNode jumpInsnNode);
   Vop ifnonnull(JumpInsnNode jumpInsnNode);
   Vop ifacmpne(JumpInsnNode jumpInsnNode);
   Vop invokestatic(SMethodDescriptor name);
   Vop invokespecial(SMethodDescriptor name);
   Vop invokeinterface(SMethodDescriptor name);
   Vop invokevirtual(SMethodDescriptor name);

   Vop got0();
}
