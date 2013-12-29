package com.lexicalscope.symb.vm;

import static java.util.Arrays.copyOf;

import com.lexicalscope.symb.vm.classloader.SMethodName;
import com.lexicalscope.symb.vm.instructions.ops.Padding;

public final class SnapshotableStackFrame implements StackFrame {
   private final Object[] stack;
   private InstructionNode instruction; // PC
   private final int opBot; // pointer to bottom of operand stack
   private int opTop; // pointer to top of operand stack
   private final int vars = 0; // pointer to local variables
   private final SMethodName name;

   public SnapshotableStackFrame(
         final SMethodName name,
         final InstructionNode instruction,
         final int maxLocals,
         final int maxStack) {
      this(name, instruction, new Object[maxLocals + maxStack], maxLocals - 1, maxLocals - 1);
   }

   private SnapshotableStackFrame(
         final SMethodName name,
         final InstructionNode instruction,
         final Object[] stack,
         final int opBot,
         final int opTop) {
      this.name = name;
      this.instruction = instruction;
      this.stack = stack;
      this.opBot = opBot;
      this.opTop = opTop;
   }

   @Override
   public StackFrame advance(final InstructionNode nextInstruction) {
      instruction = nextInstruction;
      return this;
   }

   @Override
   public StackFrame push(final Object val) {
      assert !(val instanceof Double);
      assert !(val instanceof Long);
      pushInternal(val);
      return this;
   }

   @Override
   public StackFrame pushDoubleWord(final Object val) {
      pushInternal(val);
      pushInternal(new Padding());
      return this;
   }

   private void pushInternal(final Object val) {
      opTop++;
      stack[opTop] = val;
   }

   @Override
   public Object pop() {
      try {
         return stack[opTop];
      } finally {
         opTop--;
      }
   }

   @Override
   public Object popDoubleWord() {
      assert peek() instanceof Padding : this;
      pop();
      return pop();
   }

   @Override
   public InstructionNode instruction() {
      return instruction;
   }

   @Override
   public StackFrame loadConst(final Object val) {
      push(val);
      return this;
   }

   @Override
   public StackFrame pushAll(final Object[] args) {
      System.arraycopy(args, 0, stack, opTop + 1, args.length);
      opTop += args.length;
      return this;
   }

   @Override
   public Object local(final int var) {
      return stack[vars + var];
   }

   @Override
   public void local(final int var, final Object val) {
      stack[vars + var] = val;
   }

   @Override
   public SnapshotableStackFrame setLocals(final Object[] args) {
      assert args.length <= stack.length - vars : String.format("asked to copy %d args into a stack of length %d", args.length, stack.length - vars);
      System.arraycopy(args, 0, stack, vars, args.length);
      return this;
   }

   @Override
   public Object[] pop(final int argCount) {
      final Object[] result = peek(argCount);
      opTop -= argCount;
      return result;
   }

   @Override
   public Object[] peek(final int argCount) {
      final Object[] result = new Object[argCount];
      System.arraycopy(stack, opTop + 1 - argCount, result, 0, argCount);
      return result;
   }

   @Override
   public Object peek() {
      return stack[opTop];
   }

   @Override
   public SnapshotableStackFrame snapshot() {
      return new SnapshotableStackFrame(name, instruction, copyOf(stack, stack.length), opBot, opTop);
   }

   @Override
   public String toString() {
      final StringBuilder locals = new StringBuilder();
      final StringBuilder operands = new StringBuilder();
      formatStack(locals, vars, opBot);
      formatStack(operands, opBot + 1, opTop);

      return String.format("%s l[%s]o[%s]", instruction, locals, operands);
   }

   private void formatStack(final StringBuilder stackString, final int start, final int end) {
      String separator = "";
      for (int i = start; i <= end; i++) {
         stackString.append(separator);
         stackString.append(stack[i]);
         separator = ", ";
      }
   }

   @Override public SMethodName method() {
      return name;
   }
}
