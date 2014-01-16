package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.tree.FieldInsnNode;

import com.lexicalscope.symb.heap.Heap;
import com.lexicalscope.symb.stack.Stack;
import com.lexicalscope.symb.stack.StackFrame;
import com.lexicalscope.symb.vm.InstructionNode;
import com.lexicalscope.symb.vm.Statics;
import com.lexicalscope.symb.vm.Vm;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;
import com.lexicalscope.symb.vm.classloader.SFieldName;
import com.lexicalscope.symb.vm.instructions.BaseInstructions;

final class GetStaticOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;

   GetStaticOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
   }

   @Override
   public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final SClass klass = statics.load(fieldInsnNode.owner);
      final Object staticsAddress = statics.whereMyStaticsAt(klass);
      final int offset = klass.staticFieldIndex(name);

      stackFrame.push(heap.get(staticsAddress, offset));
   }

   @Override
   public String toString() {
      return "GETSTATIC " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}