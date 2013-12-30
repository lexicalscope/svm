package com.lexicalscope.symb.vm.instructions;

import static com.google.common.collect.Lists.reverse;

import java.util.ArrayList;
import java.util.List;

import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionInternalNode;
import com.lexicalscope.symb.vm.classloader.MethodBody;

public class StatementBuilder {
   private final List<Instruction> instructions = new ArrayList<>();
   private int maxStack;
   private int maxLocals;
   private final BaseInstructions baseInstructions;

   public StatementBuilder(final BaseInstructions baseInstructions) {
      this.baseInstructions = baseInstructions;
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
      add(baseInstructions.newObject(klassDesc));
      return this;
   }

   public StatementBuilder aconst_null() {
      add(baseInstructions.aconst_null());
      return this;
   }

   public StatementBuilder iconst_0() {
      add(baseInstructions.iconst_0());
      return this;
   }

   public StatementBuilder iconst(final int i) {
      add(baseInstructions.iconst(i));
      return this;
   }

   public StatementBuilder lconst(final long l) {
      add(baseInstructions.lconst(l));
      return this;
   }

   public StatementBuilder return1() {
      add(baseInstructions.return1());
      return this;
   }

   public StatementBuilder return2() {
      add(baseInstructions.return2());
      return this;
   }

   public StatementBuilder returnVoid() {
      add(baseInstructions.returnVoid());
      return this;
   }

   public StatementBuilder addressToHashCode() {
      add(baseInstructions.addressToHashCode());
      return this;
   }

   public StatementBuilder aload(final int index) {
      add(baseInstructions.aload(index));
      return this;
   }

   public StatementBuilder nanoTime() {
      add(baseInstructions.nanoTime());
      return this;
   }

   public StatementBuilder currentTimeMillis() {
      add(baseInstructions.currentTimeMillis());
      return this;
   }

   public StatementBuilder currentThread() {
      add(baseInstructions.currentThread());
      return this;
   }

   public StatementBuilder arrayCopy() {
      add(baseInstructions.arrayCopy());
      return this;
   }

   public StatementBuilder floatToRawIntBits() {
      add(baseInstructions.floatToRawIntBits());
      return this;
   }

   public StatementBuilder doubleToRawLongBits() {
      add(baseInstructions.doubleToRawLongBits());
      return this;
   }

   public StatementBuilder fload(final int index) {
      add(baseInstructions.fload(index));
      return this;
   }

   public StatementBuilder dload(final int index) {
      add(baseInstructions.dload(index));
      return this;
   }

   public StatementBuilder getCallerClass() {
      add(baseInstructions.getCallerClass());
      return this;
   }

   private void add(final Instruction instruction) {
      instructions.add(instruction);
   }

   public MethodBody build() {
      InstructionInternalNode next = null;
      for (final Instruction instruction : reverse(instructions)) {
         final InstructionInternalNode node = new InstructionInternalNode(instruction);
         if(next != null) {node.next(next);}
         next = node;
      }
      return new MethodBody(next, maxStack, maxLocals);
   }
}
