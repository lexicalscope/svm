package com.lexicalscope.svm.j.instruction.concrete.klass;

import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.Lists;
import com.lexicalscope.svm.j.instruction.InstructionInternal;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.factory.Instructions;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.JavaConstants;
import com.lexicalscope.symb.vm.j.Op;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.code.AsmSMethodName;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

public class LoadingInstruction implements Vop {
   private final Op<List<SClass>> loader;
   private final Vop op;
   private final Instructions instructions;

   public LoadingInstruction(
         final List<String> klassNames,
         final Vop op,
         final Instructions instructions) {
      this(new DefineClassOp(klassNames), op, instructions);
   }

   public LoadingInstruction(
         final Op<List<SClass>> loader,
         final Vop op,
         final Instructions instructions) {
      this.loader = loader;
      this.op = op;
      this.instructions = instructions;
   }

   public LoadingInstruction(final String klassName, final Vop op, final Instructions instructions) {
      this(asList(klassName), op, instructions);
   }

   @Override public void eval(final State ctx) {
      final List<SClass> definedClasses = loader.eval(ctx);
      if(definedClasses.isEmpty()){
         new LinearInstruction(op).eval(ctx);
      } else {
         final StatementBuilder replacementInstruction = instructions.statements();
         for (final SClass klass : Lists.reverse(definedClasses)) {
            if(klass.hasStaticInitialiser())
            {
               replacementInstruction
                  .createInvokeStatic(new AsmSMethodName(klass.name(), JavaConstants.CLINIT, JavaConstants.NOARGS_VOID_DESC))
                  .createClassDefaultConstructor(klass.name());
            }
         }
         final InstructionInternal replacement = replacementInstruction.linear(op).buildInstruction();
         replacement.nextIs(ctx.instructionNext());
         ctx.advanceTo(replacement);
      }
   }

   @Override public String toString() {
      return "(loading " + loader + ") " + op.toString();
   }
}
