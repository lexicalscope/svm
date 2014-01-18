package com.lexicalscope.svm.j.instruction.concrete.klass;

import static com.lexicalscope.symb.klass.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.symb.klass.SClass;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class InstanceOfOp implements Vop {
   private final String klassName;

   public InstanceOfOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(final State ctx) {
      final Object address = ctx.pop();
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
}
