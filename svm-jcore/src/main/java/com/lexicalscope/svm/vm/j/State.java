package com.lexicalscope.svm.vm.j;

import java.util.List;

import com.lexicalscope.svm.heap.Allocatable;
import com.lexicalscope.svm.metastate.MetaFactory;
import com.lexicalscope.svm.metastate.MetaKey;
import com.lexicalscope.svm.stack.Stack;
import com.lexicalscope.svm.stack.StackFrame;
import com.lexicalscope.svm.stack.trace.SStackTrace;
import com.lexicalscope.svm.state.Snapshotable;
import com.lexicalscope.svm.vm.FlowNode;
import com.lexicalscope.svm.vm.j.klass.SClass;

public interface State extends Snapshotable<State>, FlowNode {
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
   void goal();

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
   Object[] peek(int count);
   Object peek();
   Object[] pop(int count);
   Object pop();
   Object[] locals(int count);
   void pushDoubleWord(Object val);
   Object popDoubleWord();
   Object peekOperand();

   <T> T getMeta(MetaKey<T> key);
   <T> void setMeta(MetaKey<T> key, T value);
   <T> void replaceMeta(MetaKey<T> key, MetaFactory<T> metaFactory);

   SStackTrace trace();
}