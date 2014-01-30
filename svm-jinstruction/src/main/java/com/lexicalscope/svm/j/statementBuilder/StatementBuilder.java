package com.lexicalscope.svm.j.statementBuilder;

import static com.google.common.collect.Lists.reverse;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.NoOp;
import com.lexicalscope.svm.j.instruction.factory.AbstractInstructionSink;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public final class StatementBuilder {
   private final Instruction insertBeforeInstruction;
   private final List<Vop> instructions = new ArrayList<>();
   private int maxStack;
   private int maxLocals;
   private final Instructions factory;

   private final InstructionSource.InstructionSink sink = new AbstractInstructionSink() {
      @Override public void nextInstruction(final Vop node) {
         instructions.add(node);
      }
   };

   public StatementBuilder(final Instructions baseInstructions) {
      this(baseInstructions, null);
   }

   public StatementBuilder(final Instructions baseInstructions, final Instruction nextInstruction) {
      this.factory = baseInstructions;
      this.insertBeforeInstruction = nextInstruction;
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

   public InstructionInternal buildInstruction() {
      InstructionInternal next = null;
      for (final Vop instruction : reverse(instructions)) {
         final InstructionInternal node = new InstructionInternal(instruction);
         if(next != null) {node.nextIs(next);}
         next = node;
      }
      if(insertBeforeInstruction != null) { next.nextIs(insertBeforeInstruction); }
      return next;
   }

   public StatementBuilder newObject(final String klassDesc) {
      source().newObject(klassDesc, sink);
      return this;
   }

   public StatementBuilder aconst_null() {
      source().aconst_null(sink);
      return this;
   }

   public StatementBuilder iconst_0() {
      source().iconst_0(sink);
      return this;
   }

   public StatementBuilder iconst(final int i) {
      source().iconst(i, sink);
      return this;
   }

   public StatementBuilder lconst(final long l) {
      source().lconst(l, sink);
      return this;
   }

   public StatementBuilder return1() {
      source().return1(sink);
      return this;
   }

   public StatementBuilder return2() {
      source().return2(sink);
      return this;
   }

   public StatementBuilder returnVoid() {
      source().returnVoid(sink);
      return this;
   }

   public StatementBuilder aload(final int index) {
      source().aload(index, sink);
      return this;
   }

   public StatementBuilder fload(final int index) {
      source().fload(index, sink);
      return this;
   }

   public StatementBuilder dload(final int index) {
      source().dload(index, sink);
      return this;
   }

   public StatementBuilder nop() {
      sink.linearInstruction(new NoOp());
      return this;
   }

   public StatementBuilder invokeInterface(final String klassName, final String methodName, final String desc) {
      source().invokeinterface(new AsmSMethodName(klassName, methodName, desc), sink);
      return this;
   }

   public StatementBuilder loadArg(final Object object) {
      source().loadArg(object, sink);
      return this;
   }

   public StatementBuilder createInvokeSpecial(final SMethodDescriptor sMethodName) {
      source().invokespecial(sMethodName, sink);
      return this;
   }

   public StatementBuilder createInvokeStatic(final SMethodDescriptor sMethodName) {
      source().invokestatic(sMethodName, sink);
      return this;
   }

   public StatementBuilder invokeConstructorOfClassObjects(final String klassName) {
      source().invokeConstructorOfClassObjects(klassName, sink);
      return this;
   }

   public StatementBuilder linear(final Vop op) {
      sink.linearInstruction(op);
      return this;
   }

   public StatementBuilder instruction(final Vop op) {
      sink.nextInstruction(op);
      return this;
   }

   public StatementBuilder loading(final List<String> classes, final Vop op) {
      sink.loadingInstruction(classes, op, factory);
      return this;
   }

   public StatementBuilder reflectionnewarray() {
      source().reflectionnewarray(sink);
      return this;
   }

   private InstructionSource source() {
      return factory.source();
   }
}
