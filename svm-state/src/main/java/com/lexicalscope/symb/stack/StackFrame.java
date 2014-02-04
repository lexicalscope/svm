package com.lexicalscope.symb.stack;

import com.lexicalscope.symb.stack.trace.SMethodName;
import com.lexicalscope.symb.state.Snapshotable;

public interface StackFrame extends Snapshotable<StackFrame> {
   StackFrame advance(Object nextInstruction);
   Object instruction();

   StackFrame push(Object val);
   StackFrame pushDoubleWord(Object val);
   StackFrame pushAll(Object[] args);

   Object pop();
   Object popDoubleWord();
   Object[] pop(int argCount);
   Object[] peek(int argCount);
   Object peek();

   StackFrame setLocals(Object[] args);
   Object local(int var);
   void local(int var, Object val);

   SMethodName context();
   boolean isDynamic();
}