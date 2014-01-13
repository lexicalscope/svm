package com.lexicalscope.symb.vm.instructions;

import static com.google.common.collect.Lists.reverse;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.classloader.MethodBody;

public final class StatementBuilder {
   private final List<Instruction> instructions = new ArrayList<>();
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

   public StatementBuilder newObject(final String klassDesc) {
      add(factory.newObject(klassDesc));
      return this;
   }

   public StatementBuilder aconst_null() {
      add(factory.aconst_null());
      return this;
   }

   public StatementBuilder iconst_0() {
      add(factory.iconst_0());
      return this;
   }

   public StatementBuilder iconst(final int i) {
      add(factory.iconst(i));
      return this;
   }

   public StatementBuilder lconst(final long l) {
      add(factory.lconst(l));
      return this;
   }

   public StatementBuilder return1() {
      add(factory.return1());
      return this;
   }

   public StatementBuilder return2() {
      add(factory.return2());
      return this;
   }

   public StatementBuilder returnVoid() {
      add(factory.returnVoid());
      return this;
   }

   public StatementBuilder addressToHashCode() {
      add(factory.addressToHashCode());
      return this;
   }

   public StatementBuilder aload(final int index) {
      add(factory.aload(index));
      return this;
   }

   public StatementBuilder nanoTime() {
      add(factory.nanoTime());
      return this;
   }

   public StatementBuilder currentTimeMillis() {
      add(factory.currentTimeMillis());
      return this;
   }

   public StatementBuilder currentThread() {
      add(factory.currentThread());
      return this;
   }

   public StatementBuilder arrayCopy() {
      add(factory.arrayCopy());
      return this;
   }

   public StatementBuilder floatToRawIntBits() {
      add(factory.floatToRawIntBits());
      return this;
   }

   public StatementBuilder doubleToRawLongBits() {
      add(factory.doubleToRawLongBits());
      return this;
   }

   public StatementBuilder fload(final int index) {
      add(factory.fload(index));
      return this;
   }

   public StatementBuilder dload(final int index) {
      add(factory.dload(index));
      return this;
   }

   public StatementBuilder getCallerClass() {
      add(factory.getCallerClass());
      return this;
   }

   public StatementBuilder getPrimitiveClass() {
      add(factory.getPrimitiveClass());
      return this;
   }

   public StatementBuilder nop() {
      add(factory.nop());
      return this;
   }

   private void add(final Instruction instruction) {
      instructions.add(instruction);
   }

   public StatementBuilder invokeInterface(final String klassName, final String methodName, final String desc) {
      add(factory.invokeInterface(klassName, methodName, desc));
      return this;
   }

   public MethodBody build() {
      return new MethodBody(buildInstruction(), maxStack, maxLocals);
   }

   public InstructionInternalNode buildInstruction() {
      InstructionInternalNode next = null;
      for (final Instruction instruction : reverse(instructions)) {
         final InstructionInternalNode node = new InstructionInternalNode(instruction);
         if(next != null) {node.next(next);}
         next = node;
      }
      return next;
   }
}
