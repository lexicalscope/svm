package com.lexicalscope.svm.j.statementBuilder;

import static com.google.common.collect.Lists.reverse;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.instruction.factory.Instructions.InstructionSink;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public final class StatementBuilder {
   private final List<Vop> instructions = new ArrayList<>();
   private int maxStack;
   private int maxLocals;
   private final Instructions factory;

   private final InstructionSink sink = new InstructionSink() {
      @Override public void noInstruction() { }

      @Override public void nextInstruction(final Vop node) {
         instructions.add(node);
      }
   };

   public StatementBuilder(final Instructions baseInstructions) {
      this.factory = baseInstructions;
   }

   public StatementBuilder maxLocals(final int maxLocals) {
      this.maxLocals = maxLocals;
      return this;
   }

   public StatementBuilder maxStack(final int maxStack) {
      this.maxStack = maxStack;
      return this;
   }

   private StatementBuilder add(final Vop instruction) {
      instructions.add(instruction);
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
      return next;
   }

   public StatementBuilder newObject(final String klassDesc) {
      return add(factory.newObject(klassDesc, sink));
   }

   public StatementBuilder aconst_null() {
      return add(factory.aconst_null(sink));
   }

   public StatementBuilder iconst_0() {
      return add(factory.iconst_0(sink));
   }

   public StatementBuilder iconst(final int i) {
      return add(factory.iconst(i, sink));
   }

   public StatementBuilder lconst(final long l) {
      return add(factory.lconst(l, sink));
   }

   public StatementBuilder return1() {
      return add(factory.return1(sink));
   }

   public StatementBuilder return2() {
      return add(factory.return2(sink));
   }

   public StatementBuilder returnVoid() {
      return add(factory.returnVoid(sink));
   }

   public StatementBuilder addressToHashCode() {
      return add(factory.addressToHashCode(sink));
   }

   public StatementBuilder aload(final int index) {
      return add(factory.aload(index, sink));
   }

   public StatementBuilder nanoTime() {
      return add(factory.nanoTime(sink));
   }

   public StatementBuilder currentTimeMillis() {
      return add(factory.currentTimeMillis(sink));
   }

   public StatementBuilder currentThread() {
      return add(factory.currentThread(sink));
   }

   public StatementBuilder arrayCopy() {
      return add(factory.arrayCopy(sink));
   }

   public StatementBuilder floatToRawIntBits() {
      return add(factory.floatToRawIntBits(sink));
   }

   public StatementBuilder doubleToRawLongBits() {
      return add(factory.doubleToRawLongBits(sink));
   }

   public StatementBuilder fload(final int index) {
      return add(factory.fload(index, sink));
   }

   public StatementBuilder dload(final int index) {
      return add(factory.dload(index, sink));
   }

   public StatementBuilder getCallerClass() {
      return add(factory.getCallerClass(sink));
   }

   public StatementBuilder getPrimitiveClass() {
      return add(factory.getPrimitiveClass(sink));
   }

   public StatementBuilder nop() {
      return add(factory.nop(sink));
   }

   public StatementBuilder invokeInterface(final String klassName, final String methodName, final String desc) {
      return add(factory.invokeInterface(klassName, methodName, desc, sink));
   }

   public StatementBuilder loadArg(final Object object) {
      return add(factory.loadArg(object, sink));
   }

   public StatementBuilder initThread() {
      return add(factory.initThread(sink));
   }

   public StatementBuilder createInvokeSpecial(final SMethodDescriptor sMethodName) {
      return add(factory.createInvokeSpecial(sMethodName, sink));
   }
}
