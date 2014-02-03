package com.lexicalscope.symb.vm.j;

import java.util.List;

import com.lexicalscope.symb.heap.Allocatable;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;
import com.lexicalscope.symb.vm.FlowNode;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.metastate.MetaKey;
import com.lexicalscope.symb.vm.j.metastate.MetaState;

public interface State extends Snapshotable<State>, FlowNode<State> {
   void pushFrame(StackFrame stackFrame);

   Object local(int var);

   void popFrame(int returnCount);

   void fork(State[] states);

   void local(int var, Object val);

   Object whereMyStaticsAt(SClass klass);

   Object whereMyClassAt(String klassName);

   StackFrame previousFrame();

   StaticsMarker staticsMarker(SClass klass);

   void staticsAt(SClass klass, Object classAddress);

   Object newObject(Allocatable klass);

   void classAt(SClass klass, Object classAddress);

   SClass classClass();

   List<SClass> defineClass(String klassName);

   SClass definePrimitiveClass(String klassName);

   boolean isDefined(String klass);

   void currentThreadIs(Object address);

   Object currentThread();

   SClass load(String klassName);

   void advanceToNextInstruction();

   Instruction instructionNext();

   Instruction instructionJmpTarget();

   void pushDoubleWord(Object val);

   Object popDoubleWord();

   void put(Object address, int offset, Object val);

   Object get(Object address, int offset);

   void advanceTo(Instruction instruction);

   Object peek();

   Object[] pop(int count);

   Object pop();

   void push(Object operand);

   Object nullPointer();

   Object peekOperand();

   SStackTrace trace();

   MetaState getMeta();
   <T> T getMeta(MetaKey<T> key);
   <T> void setMeta(MetaKey<T> key, T value);

   State[] fork();

   Stack stack();

   Instruction instruction();

   Object hashCode(Object object);
}