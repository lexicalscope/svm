package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.concrete.array.ArrayCopyOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.GetPrimitiveClass;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
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
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public final class BaseInstructions implements Instructions {
   private final InstructionFactory instructionFactory;
   private final InstructionSource instructionSource;
   private final InstructionHelper instructionHelper;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
      this.instructionHelper = new InstructionHelper(instructionFactory);
      this.instructionSource = new BaseInstructionSource(this, instructionFactory, instructionHelper);
   }

   @Override public void instructionFor(
         final AbstractInsnNode abstractInsnNode,
         final InstructionSink sink) {

      switch (abstractInsnNode.getType()) {
         case AbstractInsnNode.LINE:
         case AbstractInsnNode.FRAME:
         case AbstractInsnNode.LABEL:
            sink.noInstruction();
            return;
      }

      new InstructionSwitch(instructionSource).instructionFor(abstractInsnNode, sink);
   }

   /*
    * Only instructions new, getstatic, putstatic, or invokestatic can cause class loading.
    */

   @Override public void aload(final int index, final InstructionSink sink) {
      instructionSource.load(index, sink);
   }

   @Override public void fload(final int index, final InstructionSink sink) {
      instructionSource.load(index, sink);
   }

   @Override public void dload(final int index, final InstructionSink sink) {
      instructionSource.load2(index, sink);
   }

   public static String fieldKey(final FieldInsnNode fieldInsnNode) {
      return String.format("%s.%s:%s", fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
   }

   @Override public Vop defineClass(final List<String> klassNames) {
      return new LoadingInstruction(klassNames, new NoOp(), this);
   }

   @Override public void initThread(final InstructionSink sink) {
      sink.linearInstruction(new InitThreadOp());
   }

   @Override public StatementBuilder statements() {
      return new StatementBuilder(this);
   }

   @Override public void addressToHashCode(final InstructionSink sink) {
      sink.linearInstruction(new AddressToHashCodeOp());
   }

   @Override public void nanoTime(final InstructionSink sink) {
      sink.linearInstruction(new NanoTimeOp());
   }

   @Override public void currentTimeMillis(final InstructionSink sink) {
      sink.linearInstruction(new CurrentTimeMillisOp());
   }

   @Override public void invokespecial(final SMethodDescriptor sMethodName, final InstructionSink sink) {
      instructionSource.invokespecial(sMethodName, sink);
   }

   @Override public void invokestatic(final SMethodDescriptor sMethodName, final InstructionSink sink) {
      instructionSource.invokestatic(sMethodName, sink);
   }

   @Override public void classDefaultConstructor(final String klassName, final InstructionSink sink) {
      MethodCallInstruction.createClassDefaultConstructor(klassName, sink);
   }

   @Override public void invokeInterface(final SMethodDescriptor sMethodName, final InstructionSink sink) {
      instructionSource.invokeinterface(sMethodName, sink);
   }

   @Override public void currentThread(final InstructionSink sink) {
      sink.linearInstruction(new CurrentThreadOp());
   }

   @Override public void arrayCopy(final InstructionSink sink) {
      sink.linearInstruction(new ArrayCopyOp());
   }

   @Override public void floatToRawIntBits(final InstructionSink sink) {
      sink.linearInstruction(new FloatToRawIntBits());
   }

   @Override public void doubleToRawLongBits(final InstructionSink sink) {
      sink.linearInstruction(new DoubleToRawLongBits());
   }

   @Override public void getCallerClass(final InstructionSink sink) {
      sink.linearInstruction(new GetCallerClass());
   }

   @Override public void getPrimitiveClass(final InstructionSink sink) {
      sink.linearInstruction(new GetPrimitiveClass());
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
   public void nop(final InstructionSink sink) {
      sink.linearInstruction(new NoOp());
   }

   @Override public void loadArg(final Object object, final InstructionSink sink) {
      sink.nextInstruction(instructionFactory.loadArg(object, this));
   }

   @Override public void iconst(final int constVal, final InstructionSink sink) {
      sink.linearInstruction(instructionHelper.iconst(constVal));
   }

   @Override public void lconst(final long constVal, final InstructionSink sink) {
      sink.linearInstruction(instructionHelper.lconst(constVal));
   }

   @Override public void newObject(final String klassDesc, final InstructionSink sink) {
      instructionSource.newObject(klassDesc, sink);
   }

   @Override public void aconst_null(final InstructionSink sink) {
      instructionSource.aconst_null(sink);
   }

   @Override public void iconst_0(final InstructionSink sink) {
      instructionSource.iconst_0(sink);
   }

   @Override public void return1(final InstructionSink sink) {
      instructionSource.return1(sink);
   }

   @Override public void return2(final InstructionSink sink) {
      instructionSource.return2(sink);
   }

   @Override public void returnVoid(final InstructionSink sink) {
      instructionSource.returnVoid(sink);
   }
}
