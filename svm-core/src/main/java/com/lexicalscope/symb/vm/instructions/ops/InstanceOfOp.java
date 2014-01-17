package com.lexicalscope.symb.vm.instructions.ops;

import static com.lexicalscope.symb.vm.classloader.SClass.OBJECT_MARKER_OFFSET;

import com.lexicalscope.symb.vm.Context;
import com.lexicalscope.symb.vm.Vop;
import com.lexicalscope.symb.vm.classloader.SClass;

public class InstanceOfOp implements Vop {
   private final String klassName;

   public InstanceOfOp(final String klassName) {
      this.klassName = klassName;
   }

   @Override public void eval(final Context ctx) {
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
