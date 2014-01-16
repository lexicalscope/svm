package com.lexicalscope.symb.vm.instructions.ops;

import org.objectweb.asm.Type;
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

final class PutStaticOp implements Vop {
   private final FieldInsnNode fieldInsnNode;
   private final SFieldName name;
   private final boolean doubleWord;

   PutStaticOp(final FieldInsnNode fieldInsnNode) {
      this.fieldInsnNode = fieldInsnNode;
      this.name = new SFieldName(fieldInsnNode.owner, fieldInsnNode.name);
      final int sort = Type.getType(fieldInsnNode.desc).getSort();
      doubleWord = sort == Type.DOUBLE || sort == Type.LONG;
   }

   @Override
   public void eval(Vm vm, final Statics statics, final Heap heap, final Stack stack, final StackFrame stackFrame, InstructionNode instructionNode) {
      final SClass klass = statics.load(fieldInsnNode.owner);
      final Object staticsAddress = statics.whereMyStaticsAt(klass);
      final int offset = klass.staticFieldIndex(name);

      Object value;
      if(doubleWord) {
         value = stackFrame.popDoubleWord();
      } else {
         value = stackFrame.pop();
      }
      heap.put(staticsAddress, offset, value);
   }

   @Override
   public String toString() {
      return "PUTSTATIC " + BaseInstructions.fieldKey(fieldInsnNode);
   }
}