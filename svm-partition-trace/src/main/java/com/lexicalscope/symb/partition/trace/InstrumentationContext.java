package com.lexicalscope.symb.partition.trace;

import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.j.Instruction;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.j.klass.SClass;
import com.lexicalscope.symb.vm.j.j.klass.SMethodDescriptor;

public class InstrumentationContext {
   private final Instruction instruction;
   private final State state;

   public InstrumentationContext(final Instruction instruction, final State state) {
      this.instruction = instruction;
      this.state = state;
   }

   public Instruction instrumentedInstruction() {
      return instruction;
   }

   public StackFrame currentFrame() {
      return state.currentFrame();
   }

   public State state() {
      return state;
   }

   public boolean instructionIsDynamicCall() {
      return instruction.code().isDynamicMethodCall();
   }

   public SClass receiverKlass() {
      final SMethodDescriptor methodName = methodCalled();
      final int argSize = methodName.argSize();
      final Object receiver = state.peek(argSize)[0];

      return (SClass) state.get(receiver, SClass.OBJECT_MARKER_OFFSET);
   }

   public SMethodDescriptor methodCalled() {
      return instruction.query(new InstructionQueryAdapter<SMethodDescriptor>() {});
   }
}
