package com.lexicalscope.svm.j.instruction.factory;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class InstructionSourceAdapter implements InstructionSource {
   private final InstructionSource delgate;

   public InstructionSourceAdapter(final InstructionSource delgate) {
      this.delgate = delgate;
   }

   @Override
   public InstructionSource load(final int var, final InstructionSink sink) {
      return delgate.load(var, sink);
   }

   @Override
   public InstructionSource load2(final int var, final InstructionSink sink) {
      return delgate.load2(var, sink);
   }

   @Override
   public InstructionSource aload(final int index, final InstructionSink sink) {
      return delgate.aload(index, sink);
   }

   @Override
   public InstructionSource fload(final int index, final InstructionSink sink) {
      return delgate.fload(index, sink);
   }

   @Override
   public InstructionSource dload(final int index, final InstructionSink sink) {
      return delgate.dload(index, sink);
   }

   @Override
   public InstructionSource store(final int var, final InstructionSink sink) {
      return delgate.store(var, sink);
   }

   @Override
   public InstructionSource store2(final int var, final InstructionSink sink) {
      return delgate.store2(var, sink);
   }

   @Override
   public InstructionSource putField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return delgate.putField(fieldInsnNode, sink);
   }

   @Override
   public InstructionSource getField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return delgate.getField(fieldInsnNode, sink);
   }

   @Override
   public InstructionSource getStaticField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return delgate.getStaticField(fieldInsnNode, sink);
   }

   @Override
   public InstructionSource putStaticField(final FieldInsnNode fieldInsnNode, final InstructionSink sink) {
      return delgate.putStaticField(fieldInsnNode, sink);
   }

   @Override
   public InstructionSource aconst_null(final InstructionSink sink) {
      return delgate.aconst_null(sink);
   }

   @Override
   public InstructionSource returnVoid(final InstructionSink sink) {
      return delgate.returnVoid(sink);
   }

   @Override
   public InstructionSource return1(final InstructionSink sink) {
      return delgate.return1(sink);
   }

   @Override
   public InstructionSource return2(final InstructionSink sink) {
      return delgate.return2(sink);
   }

   @Override
   public InstructionSource iand(final InstructionSink sink) {
      return delgate.iand(sink);
   }

   @Override
   public InstructionSource iadd(final InstructionSink sink) {
      return delgate.iadd(sink);
   }

   @Override
   public InstructionSource imul(final InstructionSink sink) {
      return delgate.imul(sink);
   }

   @Override
   public InstructionSource isub(final InstructionSink sink) {
      return delgate.isub(sink);
   }

   @Override
   public InstructionSource ineg(final InstructionSink sink) {
      return delgate.ineg(sink);
   }

   @Override
   public InstructionSource ishl(final InstructionSink sink) {
      return delgate.ishl(sink);
   }

   @Override
   public InstructionSource ishr(final InstructionSink sink) {
      return delgate.ishr(sink);
   }

   @Override
   public InstructionSource iushr(final InstructionSink sink) {
      return delgate.iushr(sink);
   }

   @Override
   public InstructionSource ior(final InstructionSink sink) {
      return delgate.ior(sink);
   }

   @Override
   public InstructionSource ixor(final InstructionSink sink) {
      return delgate.ixor(sink);
   }

   @Override
   public InstructionSource lushr(final InstructionSink sink) {
      return delgate.lushr(sink);
   }

   @Override
   public InstructionSource iinc(final IincInsnNode iincInsnNode, final InstructionSink sink) {
      return delgate.iinc(iincInsnNode, sink);
   }

   @Override
   public InstructionSource i2l(final InstructionSink sink) {
      return delgate.i2l(sink);
   }

   @Override
   public InstructionSource i2f(final InstructionSink sink) {
      return delgate.i2f(sink);
   }

   @Override
   public InstructionSource iconst(final int val, final InstructionSink sink) {
      return delgate.iconst(val, sink);
   }

   @Override
   public InstructionSource iconst_m1(final InstructionSink sink) {
      return delgate.iconst_m1(sink);
   }

   @Override
   public InstructionSource iconst_0(final InstructionSink sink) {
      return delgate.iconst_0(sink);
   }

   @Override
   public InstructionSource iconst_1(final InstructionSink sink) {
      return delgate.iconst_1(sink);
   }

   @Override
   public InstructionSource iconst_2(final InstructionSink sink) {
      return delgate.iconst_2(sink);
   }

   @Override
   public InstructionSource iconst_3(final InstructionSink sink) {
      return delgate.iconst_3(sink);
   }

   @Override
   public InstructionSource iconst_4(final InstructionSink sink) {
      return delgate.iconst_4(sink);
   }

   @Override
   public InstructionSource iconst_5(final InstructionSink sink) {
      return delgate.iconst_5(sink);
   }

   @Override
   public InstructionSource ifge(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifge(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ifgt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifgt(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ifle(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifle(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource iflt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.iflt(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ifeq(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifeq(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ifne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifne(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ificmpeq(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ificmpeq(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ificmpne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ificmpne(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ificmple(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ificmple(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ificmplt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ificmplt(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ificmpgt(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ificmpgt(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ificmpge(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ificmpge(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource fmul(final InstructionSink sink) {
      return delgate.fmul(sink);
   }

   @Override
   public InstructionSource fdiv(final InstructionSink sink) {
      return delgate.fdiv(sink);
   }

   @Override
   public InstructionSource f2i(final InstructionSink sink) {
      return delgate.f2i(sink);
   }

   @Override
   public InstructionSource fcmpg(final InstructionSink sink) {
      return delgate.fcmpg(sink);
   }

   @Override
   public InstructionSource fcmpl(final InstructionSink sink) {
      return delgate.fcmpl(sink);
   }

   @Override
   public InstructionSource fconst_0(final InstructionSink sink) {
      return delgate.fconst_0(sink);
   }

   @Override
   public InstructionSource land(final InstructionSink sink) {
      return delgate.land(sink);
   }

   @Override
   public InstructionSource lconst(final long val, final InstructionSink sink) {
      return delgate.lconst(val, sink);
   }

   @Override
   public InstructionSource lconst_0(final InstructionSink sink) {
      return delgate.lconst_0(sink);
   }

   @Override
   public InstructionSource lconst_1(final InstructionSink sink) {
      return delgate.lconst_1(sink);
   }

   @Override
   public InstructionSource l2i(final InstructionSink sink) {
      return delgate.l2i(sink);
   }

   @Override
   public InstructionSource lcmp(final InstructionSink sink) {
      return delgate.lcmp(sink);
   }

   @Override
   public InstructionSource sipush(final int val, final InstructionSink sink) {
      return delgate.sipush(val, sink);
   }

   @Override
   public InstructionSource bipush(final int val, final InstructionSink sink) {
      return delgate.bipush(val, sink);
   }

   @Override
   public InstructionSource dup(final InstructionSink sink) {
      return delgate.dup(sink);
   }

   @Override
   public InstructionSource dup_x1(final InstructionSink sink) {
      return delgate.dup_x1(sink);
   }

   @Override
   public InstructionSource pop(final InstructionSink sink) {
      return delgate.pop(sink);
   }

   @Override
   public InstructionSource newarray(final int val, final InstructionSink sink) {
      return delgate.newarray(val, sink);
   }

   @Override
   public InstructionSource anewarray(final InstructionSink sink) {
      return delgate.anewarray(sink);
   }

   @Override
   public InstructionSource reflectionnewarray(final InstructionSink sink) {
      return delgate.reflectionnewarray(sink);
   }

   @Override
   public InstructionSource caStore(final InstructionSink sink) {
      return delgate.caStore(sink);
   }

   @Override
   public InstructionSource iaStore(final InstructionSink sink) {
      return delgate.iaStore(sink);
   }

   @Override
   public InstructionSource aaStore(final InstructionSink sink) {
      return delgate.aaStore(sink);
   }

   @Override
   public InstructionSource caload(final InstructionSink sink) {
      return delgate.caload(sink);
   }

   @Override
   public InstructionSource iaload(final InstructionSink sink) {
      return delgate.iaload(sink);
   }

   @Override
   public InstructionSource aaload(final InstructionSink sink) {
      return delgate.aaload(sink);
   }

   @Override
   public InstructionSource arrayLength(final InstructionSink sink) {
      return delgate.arrayLength(sink);
   }

   @Override
   public InstructionSource ldcInt(final int cst, final InstructionSink sink) {
      return delgate.ldcInt(cst, sink);
   }

   @Override
   public InstructionSource ldcLong(final long cst, final InstructionSink sink) {
      return delgate.ldcLong(cst, sink);
   }

   @Override
   public InstructionSource ldcFloat(final float cst, final InstructionSink sink) {
      return delgate.ldcFloat(cst, sink);
   }

   @Override
   public InstructionSource ldcDouble(final double cst, final InstructionSink sink) {
      return delgate.ldcDouble(cst, sink);
   }

   @Override
   public InstructionSource stringPoolLoad(final String cst, final InstructionSink sink) {
      return delgate.stringPoolLoad(cst, sink);
   }

   @Override
   public InstructionSource objectPoolLoad(final Type toLoad, final InstructionSink sink) {
      return delgate.objectPoolLoad(toLoad, sink);
   }

   @Override
   public InstructionSource newObject(final String desc, final InstructionSink sink) {
      return delgate.newObject(desc, sink);
   }

   @Override
   public InstructionSource instance0f(final TypeInsnNode typeInsnNode, final InstructionSink sink) {
      return delgate.instance0f(typeInsnNode, sink);
   }

   @Override
   public InstructionSource checkcast(final TypeInsnNode typeInsnNode, final InstructionSink sink) {
      return delgate.checkcast(typeInsnNode, sink);
   }

   @Override
   public InstructionSource ifnull(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifnull(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ifnonnull(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifnonnull(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource ifacmpne(final JumpInsnNode jumpInsnNode, final InstructionSink sink) {
      return delgate.ifacmpne(jumpInsnNode, sink);
   }

   @Override
   public InstructionSource invokestatic(final SMethodDescriptor name, final InstructionSink sink) {
      return delgate.invokestatic(name, sink);
   }

   @Override
   public InstructionSource invokespecial(final SMethodDescriptor name, final InstructionSink sink) {
      return delgate.invokespecial(name, sink);
   }

   @Override
   public InstructionSource invokeinterface(final SMethodDescriptor name, final InstructionSink sink) {
      return delgate.invokeinterface(name, sink);
   }

   @Override
   public InstructionSource invokevirtual(final SMethodDescriptor name, final InstructionSink sink) {
      return delgate.invokevirtual(name, sink);
   }

   @Override
   public InstructionSource got0(final InstructionSink sink) {
      return delgate.got0(sink);
   }

   @Override
   public InstructionSource invokeConstructorOfClassObjects(final String klassName, final InstructionSink sink) {
      return delgate.invokeConstructorOfClassObjects(klassName, sink);
   }

   @Override
   public InstructionSource loadArg(final Object object, final InstructionSink sink) {
      return delgate.loadArg(object, sink);
   }

   @Override
   public Object initialFieldValue(final String desc) {
      return delgate.initialFieldValue(desc);
   }

   @Override
   public StatementBuilder statements() {
      return delgate.statements();
   }

   @Override
   public StatementBuilder before(final Instruction nextInstruction) {
      return delgate.before(nextInstruction);
   }
}
