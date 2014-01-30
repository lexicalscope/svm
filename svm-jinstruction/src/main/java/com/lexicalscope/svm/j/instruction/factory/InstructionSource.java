package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public interface InstructionSource {
   public interface InstructionSink {
      void linearInstruction(Vop node);
      void loadingInstruction(List<String> classes, Vop op, Instructions factory);
      void nextInstruction(Vop node);
      void noInstruction();
   }

   InstructionSource load(int var, InstructionSource.InstructionSink sink);
   InstructionSource load2(int var, InstructionSource.InstructionSink sink);
   InstructionSource aload(int index, InstructionSource.InstructionSink sink);
   InstructionSource fload(int index, InstructionSource.InstructionSink sink);
   InstructionSource dload(int index, InstructionSource.InstructionSink sink);
   InstructionSource store(int var, InstructionSource.InstructionSink sink);
   InstructionSource store2(int var, InstructionSource.InstructionSink sink);

   InstructionSource putField(FieldInsnNode fieldInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource getField(FieldInsnNode fieldInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource getStaticField(FieldInsnNode fieldInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource putStaticField(FieldInsnNode fieldInsnNode, InstructionSource.InstructionSink sink);

   InstructionSource aconst_null(InstructionSource.InstructionSink sink);

   InstructionSource returnVoid(InstructionSource.InstructionSink sink);
   InstructionSource return1(InstructionSource.InstructionSink sink);
   InstructionSource return2(InstructionSource.InstructionSink sink);

   InstructionSource iand(InstructionSource.InstructionSink sink);
   InstructionSource iadd(InstructionSource.InstructionSink sink);
   InstructionSource imul(InstructionSource.InstructionSink sink);
   InstructionSource isub(InstructionSource.InstructionSink sink);
   InstructionSource ineg(InstructionSource.InstructionSink sink);
   InstructionSource ishl(InstructionSource.InstructionSink sink);
   InstructionSource ishr(InstructionSource.InstructionSink sink);
   InstructionSource iushr(InstructionSource.InstructionSink sink);
   InstructionSource ior(InstructionSource.InstructionSink sink);
   InstructionSource ixor(InstructionSource.InstructionSink sink);
   InstructionSource lushr(InstructionSource.InstructionSink sink);
   InstructionSource iinc(IincInsnNode iincInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource i2l(InstructionSource.InstructionSink sink);
   InstructionSource i2f(InstructionSource.InstructionSink sink);
   InstructionSource iconst(int val, InstructionSource.InstructionSink sink);
   InstructionSource iconst_m1(InstructionSource.InstructionSink sink);
   InstructionSource iconst_0(InstructionSource.InstructionSink sink);
   InstructionSource iconst_1(InstructionSource.InstructionSink sink);
   InstructionSource iconst_2(InstructionSource.InstructionSink sink);
   InstructionSource iconst_3(InstructionSource.InstructionSink sink);
   InstructionSource iconst_4(InstructionSource.InstructionSink sink);
   InstructionSource iconst_5(InstructionSource.InstructionSink sink);
   InstructionSource ifge(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifgt(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifle(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource iflt(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifeq(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifne(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ificmpeq(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ificmpne(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ificmple(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ificmplt(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ificmpgt(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ificmpge(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);

   InstructionSource fmul(InstructionSource.InstructionSink sink);
   InstructionSource fdiv(InstructionSource.InstructionSink sink);
   InstructionSource f2i(InstructionSource.InstructionSink sink);
   InstructionSource fcmpg(InstructionSource.InstructionSink sink);
   InstructionSource fcmpl(InstructionSource.InstructionSink sink);
   InstructionSource fconst_0(InstructionSource.InstructionSink sink);

   InstructionSource land(InstructionSource.InstructionSink sink);
   InstructionSource lconst(long val, InstructionSource.InstructionSink sink);
   InstructionSource lconst_0(InstructionSource.InstructionSink sink);
   InstructionSource lconst_1(InstructionSource.InstructionSink sink);
   InstructionSource l2i(InstructionSource.InstructionSink sink);
   InstructionSource lcmp(InstructionSource.InstructionSink sink);

   InstructionSource sipush(int val, InstructionSource.InstructionSink sink);
   InstructionSource bipush(int val, InstructionSource.InstructionSink sink);

   InstructionSource dup(InstructionSource.InstructionSink sink);
   InstructionSource dup_x1(InstructionSource.InstructionSink sink);
   InstructionSource pop(InstructionSource.InstructionSink sink);

   InstructionSource newarray(int val, InstructionSource.InstructionSink sink);
   InstructionSource anewarray(InstructionSource.InstructionSink sink);
   InstructionSource reflectionnewarray(InstructionSource.InstructionSink sink);
   InstructionSource caStore(InstructionSource.InstructionSink sink);
   InstructionSource iaStore(InstructionSource.InstructionSink sink);
   InstructionSource aaStore(InstructionSource.InstructionSink sink);
   InstructionSource caload(InstructionSource.InstructionSink sink);
   InstructionSource iaload(InstructionSource.InstructionSink sink);
   InstructionSource aaload(InstructionSource.InstructionSink sink);
   InstructionSource arrayLength(InstructionSource.InstructionSink sink);

   InstructionSource ldcInt(int cst, InstructionSource.InstructionSink sink);
   InstructionSource ldcLong(long cst, InstructionSource.InstructionSink sink);
   InstructionSource ldcFloat(float cst, InstructionSource.InstructionSink sink);
   InstructionSource ldcDouble(double cst, InstructionSource.InstructionSink sink);
   InstructionSource stringPoolLoad(String cst, InstructionSource.InstructionSink sink);
   InstructionSource objectPoolLoad(Type toLoad, InstructionSource.InstructionSink sink);

   InstructionSource newObject(String desc, InstructionSource.InstructionSink sink);
   InstructionSource instance0f(TypeInsnNode typeInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource checkcast(TypeInsnNode typeInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifnull(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifnonnull(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource ifacmpne(JumpInsnNode jumpInsnNode, InstructionSource.InstructionSink sink);
   InstructionSource invokestatic(SMethodDescriptor name, InstructionSource.InstructionSink sink);
   InstructionSource invokespecial(SMethodDescriptor name, InstructionSource.InstructionSink sink);
   InstructionSource invokeinterface(SMethodDescriptor name, InstructionSource.InstructionSink sink);
   InstructionSource invokevirtual(SMethodDescriptor name, InstructionSource.InstructionSink sink);
   /**
    * The JVM is responsible for creating class objects (that is objects that represent the
    * class of other objects).
    */
   InstructionSource invokeConstructorOfClassObjects(String klassName, InstructionSource.InstructionSink sink);

   InstructionSource got0(InstructionSource.InstructionSink sink);
}
