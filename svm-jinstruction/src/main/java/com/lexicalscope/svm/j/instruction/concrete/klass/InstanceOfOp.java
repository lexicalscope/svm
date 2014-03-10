package com.lexicalscope.svm.j.instruction.concrete.klass;

import static com.lexicalscope.svm.vm.j.klass.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.svm.heap.ObjectRef;
import com.lexicalscope.svm.vm.j.InstructionQuery;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.vm.j.Vop;
import com.lexicalscope.svm.vm.j.klass.SClass;

public class InstanceOfOp implements Vop {
   private final String klassName;

   public InstanceOfOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(final JState ctx) {
      final ObjectRef address = (ObjectRef) ctx.pop();
      if(!ctx.nullPointer().equals(address)) {
         ctx.push(1);
         return;
      }

      final SClass classFromHeap = (SClass) ctx.get(address, OBJECT_MARKER_OFFSET);
      final SClass classFromInstruction = ctx.load(klassName);

      if(classFromHeap.instanceOf(classFromInstruction)) {
         ctx.push(1);
         return;
      }
      ctx.push(0);
   }

   @Override public String toString() {
      return "INSTANCEOF";
   }

   @Override public <T> T query(final InstructionQuery<T> instructionQuery) {
      return instructionQuery.instance0f();
   }
}
