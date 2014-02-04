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
   Stack stack();
   void pushFrame(StackFrame stackFrame);
   void popFrame(int returnCount);
   StackFrame previousFrame();
   StackFrame currentFrame();

   void advanceToNextInstruction();
   void advanceTo(Instruction instruction);
   Instruction instructionNext();
   Instruction instructionJmpTarget();
   Instruction instruction();

   Object local(int var);
   void local(int var, Object val);

   void fork(State[] states);
   State[] fork();

   Object whereMyStaticsAt(SClass klass);
   void staticsAt(SClass klass, Object classAddress);
   Object whereMyClassAt(String klassName);
   void classAt(SClass klass, Object classAddress);

   StaticsMarker staticsMarker(SClass klass);
   List<SClass> defineClass(String klassName);
   SClass definePrimitiveClass(String klassName);
   boolean isDefined(String klass);
   SClass load(String klassName);

   Object newObject(Allocatable klass);
   Object hashCode(Object object);
   Object nullPointer();
   void put(Object address, int offset, Object val);
   Object get(Object address, int offset);

   SClass classClass();

   void currentThreadIs(Object address);
   Object currentThread();

   void push(Object operand);
   Object peek();
   Object[] pop(int count);
   Object pop();
   void pushDoubleWord(Object val);
   Object popDoubleWord();
   Object peekOperand();

   MetaState getMeta();
   <T> T getMeta(MetaKey<T> key);
   <T> void setMeta(MetaKey<T> key, T value);

   SStackTrace trace();
}