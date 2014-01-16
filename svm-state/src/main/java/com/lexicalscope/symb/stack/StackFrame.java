package com.lexicalscope.symb.stack;

import com.lexicalscope.symb.state.SMethodName;
import com.lexicalscope.symb.state.Snapshotable;

public interface StackFrame extends Snapshotable<StackFrame> {
   StackFrame advance(Object nextInstruction);

   StackFrame push(Object val);
   StackFrame pushDoubleWord(Object val);
   StackFrame pushAll(Object[] args);

   Object pop();
   Object popDoubleWord();

   Object instruction();

   StackFrame loadConst(Object val);

   Object local(int var);
   void local(int var, Object val);

   StackFrame setLocals(Object[] args);

   Object[] pop(int argCount);
   Object[] peek(int argCount);
   Object peek();

   SMethodName methodName();
   String receiverKlass();
}