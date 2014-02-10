package com.lexicalscope.svm.partition.trace;

import com.lexicalscope.svm.stack.StackFrame;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.vm.j.klass.SClass;
import com.lexicalscope.svm.vm.j.klass.SMethodDescriptor;

public class InstrumentationContext {
   private final State state;
   private final SMethodDescriptor methodName;

   public InstrumentationContext(final SMethodDescriptor methodName, final State state) {
      this.methodName = methodName;
      this.state = state;
   }

   public StackFrame currentFrame() {
      return state.currentFrame();
   }

   public State state() {
      return state;
   }

   public SClass receiverKlass() {
      final int argSize = methodName.argSize();
      final Object receiver = state.peek(argSize)[0];

      return (SClass) state.get(receiver, SClass.OBJECT_MARKER_OFFSET);
   }
}
