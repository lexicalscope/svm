package com.lexicalscope.symb.vm.instructions;

import static com.lexicalscope.symb.vm.instructions.ops.Ops.nextInstruction;

import com.lexicalscope.symb.vm.Heap;
import com.lexicalscope.symb.vm.Instruction;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Op;
import com.lexicalscope.symb.vm.Stack;
import com.lexicalscope.symb.vm.StackFrame;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class LinearInstruction implements Instruction {
   private final Vop op;

   public LinearInstruction(final Vop op) {
      this.op = op;
   }

   public LinearInstruction(final Op<?> op) {
      this(new Vop() {
         @Override public void eval(final StackFrame stackFrame, final Stack stack, final Heap heap, final Statics statics) {
            op.eval(stackFrame, stack, heap, statics);
         }

         @Override public String toString() {
            return op.toString();
         }
      });
   }

   @Override
   public void eval(final Vm vm, final State state, final InstructionNode instruction) {
      state.op(nextInstruction(instruction));
      state.op(op);
   }

   @Override
   public String toString() {
      return op.toString();
   }
}
