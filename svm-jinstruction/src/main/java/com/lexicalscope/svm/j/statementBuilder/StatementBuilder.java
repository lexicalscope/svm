package com.lexicalscope.svm.j.statementBuilder;

import static com.google.common.collect.Lists.reverse;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.symb.vm.j.MethodBody;
import com.lexicalscope.symb.vm.j.Vop;

public final class StatementBuilder {
   private final List<Vop> instructions = new ArrayList<>();
   private int maxStack;
   private int maxLocals;
   private final Instructions factory;

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
      return add(factory.newObject(klassDesc));
   }

   public StatementBuilder aconst_null() {
      return add(factory.aconst_null());
   }

   public StatementBuilder iconst_0() {
      return add(factory.iconst_0());
   }

   public StatementBuilder iconst(final int i) {
      return add(factory.iconst(i));
   }

   public StatementBuilder lconst(final long l) {
      return add(factory.lconst(l));
   }

   public StatementBuilder return1() {
      return add(factory.return1());
   }

   public StatementBuilder return2() {
      return add(factory.return2());
   }

   public StatementBuilder returnVoid() {
      return add(factory.returnVoid());
   }

   public StatementBuilder addressToHashCode() {
      return add(factory.addressToHashCode());
   }

   public StatementBuilder aload(final int index) {
      return add(factory.aload(index));
   }

   public StatementBuilder nanoTime() {
      return add(factory.nanoTime());
   }

   public StatementBuilder currentTimeMillis() {
      return add(factory.currentTimeMillis());
   }

   public StatementBuilder currentThread() {
      return add(factory.currentThread());
   }

   public StatementBuilder arrayCopy() {
      return add(factory.arrayCopy());
   }

   public StatementBuilder floatToRawIntBits() {
      return add(factory.floatToRawIntBits());
   }

   public StatementBuilder doubleToRawLongBits() {
      return add(factory.doubleToRawLongBits());
   }

   public StatementBuilder fload(final int index) {
      return add(factory.fload(index));
   }

   public StatementBuilder dload(final int index) {
      return add(factory.dload(index));
   }

   public StatementBuilder getCallerClass() {
      return add(factory.getCallerClass());
   }

   public StatementBuilder getPrimitiveClass() {
      return add(factory.getPrimitiveClass());
   }

   public StatementBuilder nop() {
      return add(factory.nop());
   }

   public StatementBuilder invokeInterface(final String klassName, final String methodName, final String desc) {
      return add(factory.invokeInterface(klassName, methodName, desc));
   }

   public StatementBuilder loadArg(final Object object) {
      return add(factory.loadArg(object));
   }
}
