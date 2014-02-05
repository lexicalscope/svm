package com.lexicalscope.svm.j.statementBuilder;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.factory.AbstractInstructionSink;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource.InstructionSink;
import com.lexicalscope.svm.j.instruction.instrumentation.InstructionCode;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public final class StatementBuilder {
   private final InstructionSource source;
   private Instruction insertBeforeInstruction;
   private int maxStack;
   private int maxLocals;

   private Instruction first;
   private Instruction next;

   private final InstructionSource.InstructionSink sink;

   public StatementBuilder(final InstructionSource source) {
      this.source = source;
      sink = new AbstractInstructionSink(source) {
         @Override public void nextInstruction(final InstructionInternal node) {
            if(next != null) {next.nextIs(node); next = node;}
            if(first == null) {first = next = node;}
         }
      };
   }

   public StatementBuilder before(final Instruction nextInstruction) {
      this.insertBeforeInstruction = nextInstruction;
      return this;
   }

   public StatementBuilder maxLocals(final int maxLocals) {
      this.maxLocals = maxLocals;
      return this;
   }

   public StatementBuilder maxStack(final int maxStack) {
      this.maxStack = maxStack;
      return this;
   }

   public MethodBody build() {
      return new MethodBody(buildInstruction(), maxStack, maxLocals);
   }

   public Instruction buildInstruction() {
      if(insertBeforeInstruction != null) { next.nextIs(insertBeforeInstruction); }
      return first;
   }

   public StatementBuilder newObject(final String klassDesc) {
      source.newobject(klassDesc, sink);
      return this;
   }

   public StatementBuilder aconst_null() {
      source.aconst_null(sink);
      return this;
   }

   public StatementBuilder iconst_0() {
      source.iconst_0(sink);
      return this;
   }

   public StatementBuilder iconst(final int i) {
      source.iconst(i, sink);
      return this;
   }

   public StatementBuilder lconst(final long l) {
      source.lconst(l, sink);
      return this;
   }

   public StatementBuilder return1() {
      source.return1(sink);
      return this;
   }

   public StatementBuilder return2() {
      source.return2(sink);
      return this;
   }

   public StatementBuilder returnVoid() {
      source.returnvoid(sink);
      return this;
   }

   public StatementBuilder aload(final int index) {
      source.aload(index, sink);
      return this;
   }

   public StatementBuilder fload(final int index) {
      source.fload(index, sink);
      return this;
   }

   public StatementBuilder dload(final int index) {
      source.dload(index, sink);
      return this;
   }

   public StatementBuilder invokeInterface(final String klassName, final String methodName, final String desc) {
      source.invokeinterface(new AsmSMethodName(klassName, methodName, desc), sink);
      return this;
   }

   public StatementBuilder loadArg(final Object object) {
      source.loadarg(object, sink);
      return this;
   }

   public StatementBuilder createInvokeSpecial(final SMethodDescriptor sMethodName) {
      source.invokespecial(sMethodName, sink);
      return this;
   }

   public StatementBuilder createInvokeStatic(final SMethodDescriptor sMethodName) {
      source.invokestatic(sMethodName, sink);
      return this;
   }

   public StatementBuilder invokeConstructorOfClassObjects(final String klassName) {
      source.invokeconstructorofclassobjects(klassName, sink);
      return this;
   }

   public StatementBuilder linear(final Vop op) {
      sink.linearOp(op, InstructionCode.synthetic);
      return this;
   }

   public StatementBuilder reflectionnewarray() {
      source.reflectionnewarray(sink);
      return this;
   }

   public static StatementBuilder statements(final InstructionSource instructions) {
      return new StatementBuilder(instructions);
   }

   public InstructionSink sink() {
      return sink;
   }
}
