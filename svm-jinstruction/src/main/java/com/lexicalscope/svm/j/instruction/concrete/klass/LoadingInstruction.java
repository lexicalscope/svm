package com.lexicalscope.svm.j.instruction.concrete.klass;

import static com.lexicalscope.svm.j.statementBuilder.StatementBuilder.statements;
import static com.lexicalscope.symb.vm.j.j.code.AsmSMethodName.staticInitialiser;
import static java.util.Arrays.asList;

import java.util.List;

import com.google.common.collect.Lists;
import com.lexicalscope.svm.j.instruction.LinearInstruction;
import com.lexicalscope.svm.j.instruction.factory.InstructionSource;
import com.lexicalscope.svm.j.statementBuilder.StatementBuilder;
import com.lexicalscope.symb.vm.j.Op;
import com.lexicalscope.symb.vm.j.State;
import com.lexicalscope.symb.vm.j.Vop;
import com.lexicalscope.symb.vm.j.j.klass.SClass;

/*
 * Only instructions new, getstatic, putstatic, or invokestatic can cause class loading.
 */
public class LoadingInstruction implements Vop {
   private final Op<List<SClass>> loader;
   private final Vop op;
   private final InstructionSource instructions;

   public LoadingInstruction(
         final List<String> klassNames,
         final Vop op,
         final InstructionSource instructions) {
      this(new DefineClassOp(klassNames), op, instructions);
   }

   public LoadingInstruction(
         final Op<List<SClass>> loader,
         final Vop op,
         final InstructionSource instructions) {
      this.loader = loader;
      this.op = op;
      this.instructions = instructions;
   }

   public LoadingInstruction(final String klassName, final Vop op, final InstructionSource instructions) {
      this(asList(klassName), op, instructions);
   }

   @Override public void eval(final State ctx) {
      final List<SClass> definedClasses = loader.eval(ctx);
      if(definedClasses.isEmpty()){
         new LinearInstruction(op).eval(ctx);
      } else {
         final StatementBuilder replacementInstruction = statements(instructions).before(ctx.instructionNext());
         for (final SClass klass : Lists.reverse(definedClasses)) {
            if(klass.hasStaticInitialiser())
            {
               replacementInstruction
                  .createInvokeStatic(staticInitialiser(klass.name()))
                  .invokeConstructorOfClassObjects(klass.name());
            }
         }
         ctx.advanceTo(replacementInstruction.linear(op).buildInstruction());
      }
   }

   @Override public String toString() {
      return "(loading " + loader + ") " + op.toString();
   }
}
