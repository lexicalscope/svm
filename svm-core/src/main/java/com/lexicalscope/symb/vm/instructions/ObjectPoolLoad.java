package com.lexicalscope.symb.vm.instructions;

import org.objectweb.asm.Type;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;

public class ObjectPoolLoad implements Vop {
   private final String internalName;

   public ObjectPoolLoad(final Type type) {
      this(type.getInternalName());
   }

   public ObjectPoolLoad(final String internalName) {
      this.internalName = internalName;
   }

   @Override public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      stackFrame.push(statics.whereMyClassAt(internalName));
   }

   @Override public String toString() {
      return "LDC object " + internalName;
   }
}
