package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.vm.classloader.SMethodName;

public interface StackFrame  extends Snapshotable<StackFrame>{
   StackFrame advance(InstructionNode nextInstruction);

   StackFrame push(Object val);

   Object pop();

   InstructionNode instruction();

   StackFrame loadConst(Object val);

   StackFrame pushAll(Object[] args);

   Object local(int var);

   void local(int var, Object val);

   StackFrame setLocals(Object[] args);

   Object[] pop(int argCount);

   Object[] peek(int argCount);

   Object peek();

   SMethodName method();
}