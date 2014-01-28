package com.lexicalscope.svm.j.instruction.factory;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.method.MethodCallInstruction;
import com.lexicalscope.svm.j.instruction.concrete.nativ3.InitThreadOp;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Vop;

public final class BaseInstructions implements Instructions {
   private final InstructionFactory instructionFactory;
   private final InstructionSource instructionSource;

   public BaseInstructions(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
      this.instructionSource = new BaseInstructionSource(this, instructionFactory);
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

      new InstructionSwitch(source()).instructionFor(abstractInsnNode, sink);
   }

   /*
    * Only instructions new, getstatic, putstatic, or invokestatic can cause class loading.
    */

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

   @Override public void classDefaultConstructor(final String klassName, final InstructionSink sink) {
      MethodCallInstruction.createClassDefaultConstructor(klassName, sink);
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

   @Override public InstructionSource source() {
      return instructionSource;
   }
}
