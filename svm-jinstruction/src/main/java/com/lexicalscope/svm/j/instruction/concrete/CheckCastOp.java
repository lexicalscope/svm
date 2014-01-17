package com.lexicalscope.svm.j.instruction.concrete;

import static com.lexicalscope.symb.vm.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.symb.vm.SClass;
import com.lexicalscope.symb.vm.State;
import com.lexicalscope.symb.vm.Vop;

public class CheckCastOp implements Vop {
   private final String klassName;

   public CheckCastOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(final State ctx) {
      final Object address = ctx.peek();
      if(!ctx.nullPointer().equals(address)) {
         return;
      }

      final SClass classFromHeap = (SClass) ctx.get(address, OBJECT_MARKER_OFFSET);
      final SClass classFromInstruction = ctx.load(klassName);

      if(classFromHeap.instanceOf(classFromInstruction)) {
         return;
      }

      ctx.pop();

      // TODO[tim]: should throw an in-game class cast exception
      throw new UnsupportedOperationException();
   }

   @Override public String toString() {
      return "CHECKCAST " + klassName;
   }
}
