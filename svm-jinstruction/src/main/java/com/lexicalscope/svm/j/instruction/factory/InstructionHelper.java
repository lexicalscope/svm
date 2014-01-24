package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.object.NewObjectOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Op;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Operator;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.VopAdapter;

final class InstructionHelper {
   private final InstructionFactory instructionFactory;

   public InstructionHelper(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
   }

   Vop dconst(final double constVal) {
      return nullary2(instructionFactory.dconst(constVal));
   }

   Nullary2Op nullary2(final Nullary2Operator nullary) {
      return new Nullary2Op(nullary);
   }


   Object initialFieldValue(final int atype) {
      /*
       * Array Type  atype
       * T_BOOLEAN    4
       * T_CHAR       5
       * T_FLOAT      6
       * T_DOUBLE     7
       * T_BYTE       8
       * T_SHORT      9
       * T_INT       10
       * T_LONG      11
       */
      switch (atype) {
         case 4:
            return false;
         case 5:
            return (char) '\u0000';
         case 6:
            return (float) 0f;
         case 7:
            return (double) 0d;
         case 8:
            return (byte) 0;
         case 9:
            return (short) 0;
         case 10:
            return instructionFactory.initInt();
         case 11:
            return (long) 0l;
      }
      throw new UnsupportedOperationException("" + atype);
   }

   Vop newOp(final String klassDesc) {
      return new VopAdapter(new NewObjectOp(klassDesc));
   }

   LoadingInstruction loadingInstruction(final String klassDesc, final Vop op, final Instructions instructions) {
      return new LoadingInstruction(klassDesc, op, instructions);
   }

   Vop lconst(final long constVal) {
      return nullary2(instructionFactory.lconst(constVal));
   }
}
