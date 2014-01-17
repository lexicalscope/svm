package com.lexicalscope.symb.vm;

import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.stack.trace.SStackTrace;
import com.lexicalscope.symb.state.Snapshotable;

public interface State extends Snapshotable<State>, FlowNode<State> {
   State[] fork();

   Stack stack();
   StackFrame stackFrame();
   InstructionNode instruction();
   Object getMeta();

   SStackTrace trace();

   Object peekOperand();
//   Object popOperand();
}