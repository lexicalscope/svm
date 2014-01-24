package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayCopyOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.GetPrimitiveClass;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentThreadOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.CurrentTimeMillisOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.DoubleToRawLongBits;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.FloatToRawIntBits;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.GetCallerClass;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.NanoTimeOp;
import com.lexicalscope.svm.j.instruction.concrete.object.AddressToHashCodeOp;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public final class BaseInstructions implements Instructions {
   private final InstructionFactory instructionFactory;
   private final InstructionSource instructionSource;
   private final InstructionHelper instructionHelper;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
      this.instructionHelper = new InstructionHelper(instructionFactory);
      this.instructionSource = new BaseInstructionSource(instructionFactory, instructionHelper);
   }

   @Override public void instructionFor(
         final AbstractInsnNode abstractInsnNode,
         final InstructionSink instructionSink) {

      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.LINE:
         case AbstractInsnNode.FRAME:
         case AbstractInsnNode.LABEL:
            instructionSink.noInstruction(abstractInsnNode);
            return;
      }

      instructionSink.nextInstruction(abstractInsnNode, new InstructionSwitch(instructionSource).instructionFor(abstractInsnNode));
   }

   /*
    * Only instructions new, getstatic, putstatic, or invokestatic can cause class loading.
    */

   @Override public Vop aload(final int index) {
      return instructionSource.load(index);
   }

   @Override public Vop fload(final int index) {
      return instructionSource.load(index);
   }

   @Override public Vop dload(final int index) {
      return instructionSource.load2(index);
   }

   public static String fieldKey(final FieldInsnNode fieldInsnNode) {
      return String.format("%s.%s:%s", fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }

   @Override public Vop defineClass(final List<String> klassNames) {
      return new LoadingInstruction(klassNames, new NoOp());
   }

   @Override public Vop initThread() {
      return new LinearInstruction(new InitThreadOp());
   }

   @Override public StatementBuilder statements() {
      return new StatementBuilder(this);
   }

   @Override public Vop addressToHashCode() {
      return instructionHelper.linearInstruction(new AddressToHashCodeOp());
   }

   @Override public Vop nanoTime() {
      return instructionHelper.linearInstruction(new NanoTimeOp());
   }

   @Override public Vop currentTimeMillis() {
      return instructionHelper.linearInstruction(new CurrentTimeMillisOp());
   }

   @Override public Vop createInvokeSpecial(final SMethodDescriptor sMethodName) {
      return instructionSource.invokespecial(sMethodName);
   }

   @Override public Vop invokeInterface(final String klassName, final String methodName, final String desc) {
      return invokeInterface(new AsmSMethodName(klassName, methodName, desc));
   }

   private Vop invokeInterface(final SMethodDescriptor sMethodName) {
      return instructionSource.invokeinterface(sMethodName);
   }

   @Override public Vop currentThread() {
      return instructionHelper.linearInstruction(new CurrentThreadOp());
   }

   @Override public Vop arrayCopy() {
      return instructionHelper.linearInstruction(new ArrayCopyOp());
   }

   @Override public Vop floatToRawIntBits() {
      return instructionHelper.linearInstruction(new FloatToRawIntBits());
   }

   @Override public Vop doubleToRawLongBits() {
      return instructionHelper.linearInstruction(new DoubleToRawLongBits());
   }

   @Override public Vop getCallerClass() {
      return instructionHelper.linearInstruction(new GetCallerClass());
   }

   @Override public Vop getPrimitiveClass() {
      return instructionHelper.linearInstruction(new GetPrimitiveClass());
   }

   @Override public Object initialFieldValue(final String desc) {
      final Type type = Type.getType(desc);
      final int sort = type.getSort();
      switch (sort) {
         case Type.OBJECT:
            return null;
         case Type.ARRAY:
            return null;
         case Type.CHAR:
            return (char) '\u0000';
         case Type.BYTE:
            return (byte) 0;
         case Type.SHORT:
            return (short) 0;
         case Type.INT:
            return instructionFactory.initInt();
         case Type.LONG:
            return 0L;
         case Type.FLOAT:
            return 0f;
         case Type.DOUBLE:
            return 0d;
         case Type.BOOLEAN:
            return false;
      }
      throw new UnsupportedOperationException("" + sort);
   }

   @Override
   public Vop nop() {
      return new LinearInstruction(new NoOp());
   }

   @Override public Vop loadArg(final Object object) {
      return instructionFactory.loadArg(object);
   }

   @Override public Vop iconst(final int constVal) {
      return instructionHelper.iconst(constVal);
   }

   @Override public Vop lconst(final long constVal) {
      return instructionHelper.lconst(constVal);
   }

   @Override public Vop newObject(final String klassDesc) {
      return instructionSource.newObject(klassDesc);
   }

   @Override public Vop aconst_null() {
      return instructionSource.aconst_null();
   }

   @Override public Vop iconst_0() {
      return instructionSource.iconst_0();
   }

   @Override public Vop return1() {
      return instructionSource.return1();
   }

   @Override public Vop return2() {
      return instructionSource.return2();
   }

   @Override public Vop returnVoid() {
      return instructionSource.returnVoid();
   }
}
