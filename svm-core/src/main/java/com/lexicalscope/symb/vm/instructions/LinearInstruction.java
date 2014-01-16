package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.advanceToNextInstruction;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vop;

public class LinearInstruction implements Instruction {
   private final Vop op;

   public LinearInstruction(final Vop op) {
      this.op = op;
   }

   public LinearInstruction(final Op<?> op) {
      this(new Vop() {
         @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame) {
            op.eval(null, statics, heap, stack, stackFrame);
         }

         @Override public String toString() {
            return op.toString();
         }
      });
   }

   @Override
   public void eval(final Vm<State> vm, final State state, final InstructionNode instruction) {
      state.op(advanceToNextInstruction(instruction), null);
      state.op(op, null);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
