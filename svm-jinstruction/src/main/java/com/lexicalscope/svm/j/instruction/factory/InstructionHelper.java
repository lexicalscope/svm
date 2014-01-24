package com.lexicalscope.svm.j.instruction.factory;

import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.concrete.klass.LoadingInstruction;
import com.lexicalscope.svm.j.instruction.concrete.object.NewObjectOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Op;
import com.lexicalscope.svm.j.instruction.concrete.ops.Binary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.BinaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Op;
import com.lexicalscope.svm.j.instruction.concrete.ops.Nullary2Operator;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.NullaryOperator;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOp;
import com.lexicalscope.svm.j.instruction.concrete.ops.UnaryOperator;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.VopAdapter;

final class InstructionHelper {
   private final InstructionFactory instructionFactory;

   public InstructionHelper(final InstructionFactory instructionFactory) {
      this.instructionFactory = instructionFactory;
   }

   LinearInstruction binaryOp(final BinaryOperator operation) {
      return linearInstruction(new BinaryOp(operation));
   }

   LinearInstruction binary2Op(final Binary2Operator operation) {
      return linearInstruction(new Binary2Op(operation));
   }

   LinearInstruction unaryOp(final UnaryOperator operation) {
      return linearInstruction(new UnaryOp(operation));
   }

   LinearInstruction linearInstruction(final Vop op) {
      return new LinearInstruction(op);
   }

   Vop fconst(final float constVal) {
      return nullary(instructionFactory.fconst(constVal));
   }

   Vop dconst(final double constVal) {
      return nullary2(instructionFactory.dconst(constVal));
   }

   LinearInstruction nullary(final NullaryOperator nullary) {
      return linearInstruction(new NullaryOp(nullary));
   }

   LinearInstruction nullary2(final Nullary2Operator nullary) {
      return linearInstruction(new Nullary2Op(nullary));
   }

   Vop iconst(final int constVal) {
      return nullary(instructionFactory.iconst(constVal));
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

   LoadingInstruction loadingInstruction(final String klassDesc, final Vop op) {
      return new LoadingInstruction(klassDesc, op);
   }

   Vop lconst(final long constVal) {
      return nullary2(instructionFactory.lconst(constVal));
   }
}
